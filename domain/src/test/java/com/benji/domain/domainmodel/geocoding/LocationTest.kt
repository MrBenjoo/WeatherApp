package com.benji.domain.domainmodel.geocoding

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import kotlin.IllegalArgumentException

internal class LocationTest {


    @Test
    fun `set latitude to 5499 and longitude to 1500 should throw an IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {Location(54.99, 15.0)}
    }

    @Test
    fun `set latitude to 5500 and longitude to 1500 should not throw an IllegalArgumentException`() {
        assertDoesNotThrow { Location(55.00, 15.0) }
    }

    @Test
    fun `set latitude to 6800 and longitude to 1500 should not throw an IllegalArgumentException`() {
        assertDoesNotThrow { Location(68.00, 15.0) }
    }


    @Test
    fun `set latitude to 5600 and longitude to 999 should throw an IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {Location(56.00, 9.99)}
    }

    @Test
    fun `set latitude to 5600 and longitude to 1000 should not throw an IllegalArgumentException`() {
        assertDoesNotThrow{Location(56.00, 10.00)}
    }

    @Test
    fun `set latitude to 5600 and longitude to 2400 should not throw an IllegalArgumentException`() {
        assertDoesNotThrow{Location(56.00, 24.00)}
    }

}