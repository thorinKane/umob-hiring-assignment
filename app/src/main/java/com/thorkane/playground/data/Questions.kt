package com.thorkane.playground.data

import kotlin.random.Random

interface Question {
    val prompt: String
    val choices: List<Choice>

    fun verify(): Boolean
}

data class Choice(
    val prompt: String,
)

// TODO don't randomize the answer checking. I need to find a clean way to delegate verification.
val questions = listOf<Question>(
    object: Question {
        override val prompt: String = "The total number of available bikes is greater 500."
        override val choices: List<Choice> = listOf(
            Choice("True"),
            Choice("False")
        )

        override fun verify(): Boolean {
           return Random.nextBoolean()
        }
    },
    object: Question {
        override val prompt: String = "What is the largest provider in this city?"
        override val choices: List<Choice> = listOf(
            Choice("Dott"),
            Choice("Lime"),
            Choice("Pony")
        )

        override fun verify(): Boolean {
            return Random.nextBoolean()
        }
    },
    object: Question {
        override val prompt: String = "Guess the shortest distance between two bikes."
        override val choices: List<Choice> = listOf(
            Choice("> 1500 meters"),
            Choice("< 10 meters")
        )

        override fun verify(): Boolean {
            return Random.nextBoolean()
        }
    },
    object: Question {
        override val prompt: String = "Guess the greatest distance between two bikes."
        override val choices: List<Choice> = listOf(
            Choice("> 1500"),
            Choice("< 500")
        )

        override fun verify(): Boolean {
            return Random.nextBoolean()
        }
    },
    object: Question {
        override val prompt: String = "Guess the percentage of bikes above 50% fuel."
        override val choices: List<Choice> =  listOf(
            Choice("> 50"),
            Choice("< 50")
        )

        override fun verify(): Boolean {
            return Random.nextBoolean()
        }
    },

    // TODO Add more questions here.
)