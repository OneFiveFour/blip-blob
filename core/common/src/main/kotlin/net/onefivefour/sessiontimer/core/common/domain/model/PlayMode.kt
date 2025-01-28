package net.onefivefour.sessiontimer.core.common.domain.model

enum class PlayMode {
    /**
     * Play all tasks in all task groups in sequence.
     */
    SEQUENCE,

    /**
     * Play a randomly chosen subset of n tasks from each task group.
     */
    N_TASKS_SHUFFLED
}
