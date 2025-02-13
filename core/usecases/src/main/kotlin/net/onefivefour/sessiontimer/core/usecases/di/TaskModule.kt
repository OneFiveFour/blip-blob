package net.onefivefour.sessiontimer.core.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.task.DeleteTaskUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.task.NewTaskUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.task.SetTaskDurationUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.task.SetTaskSortOrdersUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.task.SetTaskTitleUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface TaskModule {

    @Binds
    @ViewModelScoped
    fun bindDeleteTaskUseCase(impl: DeleteTaskUseCaseImpl): DeleteTaskUseCase

    @Binds
    @ViewModelScoped
    fun bindNewTaskUseCase(impl: NewTaskUseCaseImpl): NewTaskUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskTitleUseCase(impl: SetTaskTitleUseCaseImpl): SetTaskTitleUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskDurationUseCase(impl: SetTaskDurationUseCaseImpl): SetTaskDurationUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskSortOrdersUseCase(impl: SetTaskSortOrdersUseCaseImpl): SetTaskSortOrdersUseCase
}
