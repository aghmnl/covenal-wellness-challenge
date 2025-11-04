package com.agus.wellnessapp.data.repository

import android.content.Context
import android.util.Log
import com.agus.wellnessapp.R
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.network.YogaApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val TAG = "SessionRepositoryImpl"

class SessionRepositoryImpl @Inject constructor(
    private val apiService: YogaApiService,
    private val context: Context,
    private val moshi: Moshi
) : SessionRepository {

    /** A one-time event flow for sending Toast messages */
    private val _networkErrors = MutableSharedFlow<String>()
    override fun getNetworkErrors() = _networkErrors.asSharedFlow()

    /** Cache for local data */
    private var allPoses: List<Pose>? = null

    /**
     * Tries to get all sessions from the remote API.
     * If it fails (timeout, etc.), it emits a Toast message
     * and returns the data from the local poses.json file.
     */
    override suspend fun getSessions(): Result<List<Pose>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "getSessions: Attempting remote fetch...")
                val remotePoses = apiService.getPoses()
                Log.i(TAG, "getSessions: Remote fetch successful.")
                allPoses = remotePoses
                Result.success(remotePoses)
            } catch (e: Exception) {
                Log.w(TAG, "getSessions: Remote fetch failed.", e)

                if (e is SocketTimeoutException) {
                    _networkErrors.emit("Could not reach server. Loading local data.")
                } else {
                    _networkErrors.emit("Network error. Loading local data.")
                }

                /** Fallback to local data */
                Result.runCatching {
                    Log.d(TAG, "getSessions: Loading local fallback...")
                    getOrParseLocalPoses()
                }.onFailure { localError ->
                    Log.e(TAG, "getSessions: FATAL. Local fallback failed.", localError)
                }
            }
        }
    }

    /**
     * Tries to get a single pose from the remote API.
     * If it fails, it emits a Toast and returns from the local file.
     */
    override suspend fun getSessionDetail(id: String): Result<Pose> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "getSessionDetail: Attempting remote fetch for id=$id")
                val remotePose = apiService.getPoseById(id)
                Log.i(TAG, "getSessionDetail: Remote fetch successful for id=$id")
                Result.success(remotePose)
            } catch (e: Exception) {
                Log.w(TAG, "getSessionDetail: Remote fetch failed for id=$id", e)

                _networkErrors.emit("Network error. Loading local data.")

                /** Fallback to local data */
                Result.runCatching {
                    Log.d(TAG, "getSessionDetail: Loading local fallback for id=$id")
                    val localPoses = getOrParseLocalPoses()
                    val pose = localPoses.find { it.id.toString() == id }
                    pose ?: throw Exception("Pose not found in local data")
                }.onFailure { localError ->
                    Log.e(TAG, "getSessionDetail: FATAL. Local fallback failed for id=$id", localError)
                }
            }
        }
    }

    /**  Helper function to load and parse the local R.raw.poses.json file. */
    private fun getOrParseLocalPoses(): List<Pose> {
        allPoses?.let {
            Log.d(TAG, "getOrParseLocalPoses: Returning cached poses.")
            return it
        }

        Log.d(TAG, "getOrParseLocalPoses: Reading and parsing R.raw.poses.json")
        val jsonString = context.resources.openRawResource(R.raw.poses)
            .bufferedReader().use { it.readText() }

        val listType = Types.newParameterizedType(List::class.java, Pose::class.java)
        val adapter = moshi.adapter<List<Pose>>(listType)

        val poses = adapter.fromJson(jsonString) ?: emptyList()
        Log.i(TAG, "getOrParseLocalPoses: Successfully parsed ${poses.size} poses from local file.")
        allPoses = poses
        return poses
    }
}