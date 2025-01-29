package net.onefivefour.sessiontimer.core.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupDefaultTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupPlayModeUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.DeleteTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.GetTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.NewTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupColorUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupDefaultTaskDurationUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupPlayModeUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupSortOrdersUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupTitleUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface TaskGroupModule {

    @Binds
    @ViewModelScoped
    fun bindDeleteTaskGroupUseCase(impl: DeleteTaskGroupUseCaseImpl): DeleteTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindGetTaskGroupUseCase(impl: GetTaskGroupUseCaseImpl): GetTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindNewTaskGroupUseCase(impl: NewTaskGroupUseCaseImpl): NewTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupTitleUseCase(impl: SetTaskGroupTitleUseCaseImpl): SetTaskGroupTitleUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupColorUseCase(impl: SetTaskGroupColorUseCaseImpl): SetTaskGroupColorUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupPlayModeUseCase(impl: SetTaskGroupPlayModeUseCaseImpl): SetTaskGroupPlayModeUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupDefaultTaskDurationUseCase(impl: SetTaskGroupDefaultTaskDurationUseCaseImpl): SetTaskGroupDefaultTaskDurationUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupSortOrderUseCase(
        impl: SetTaskGroupSortOrdersUseCaseImpl
    ): SetTaskGroupSortOrdersUseCase
}
