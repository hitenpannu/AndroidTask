package com.hitenderpannu.workout.data.network

import com.hitenderpannu.common.entity.NetworkResponse
import com.hitenderpannu.common.entity.Status
import com.hitenderpannu.common.entity.StatusCode
import com.hitenderpannu.workout.data.network.network_entity.NetworkBodyPart
import com.hitenderpannu.workout.data.network.network_entity.NetworkEquipment
import com.hitenderpannu.workout.data.network.network_entity.NetworkExercise
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.entity.FailedToGetExercises
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteExerciseRepoImplTest {

    @Mock
    lateinit var exerciseApi: ExerciseApi

    @InjectMocks
    lateinit var remoteExerciseRepo: RemoteExerciseRepoImpl

    @Test
    fun `should throw error if status code is not success`() {
        runBlocking {
            Mockito.`when`(exerciseApi.getAllExercises())
                .thenReturn(NetworkResponse(Status(404, "")))

            try {
                remoteExerciseRepo.getAllExercise()
            } catch (error: Throwable) {
                Assert.assertTrue(error is FailedToGetExercises)
            }
        }
    }

    @Test
    fun `should throw error if status code is success but data is null`() {
        runBlocking {
            Mockito.`when`(exerciseApi.getAllExercises())
                .thenReturn(NetworkResponse(Status(StatusCode.SUCCESS.code, "")))

            try {
                remoteExerciseRepo.getAllExercise()
            } catch (error: Throwable) {
                Assert.assertTrue(error is FailedToGetExercises)
            }
        }
    }

    @Test
    fun `should return exercise after mapping to local entity`() {
        runBlocking {
            val equipments = listOf(NetworkEquipment("equipmentId", "equipmentName"))
            val bodyParts = listOf(NetworkBodyPart("bodyPartId", "bodyPartName"))
            val exerciseData = listOf(NetworkExercise("exerciseId", "exerciseName",
                bodyParts, equipments))

            Mockito.`when`(exerciseApi.getAllExercises())
                .thenReturn(NetworkResponse(Status(StatusCode.SUCCESS.code, ""), exerciseData))

            val result = remoteExerciseRepo.getAllExercise()

            Assert.assertEquals(1, result.size)
            with(result.first()){
                Assert.assertEquals("exerciseId", this.id)
                Assert.assertEquals("exerciseName", this.name)
            }
            with(result.first().bodyParts.first()){
                Assert.assertEquals("bodyPartId", this.id)
                Assert.assertEquals("bodyPartName", this.name)
            }
            with(result.first().equipments.first()){
                Assert.assertEquals("equipmentId", this.id)
                Assert.assertEquals("equipmentName", this.name)
            }
        }
    }
}
