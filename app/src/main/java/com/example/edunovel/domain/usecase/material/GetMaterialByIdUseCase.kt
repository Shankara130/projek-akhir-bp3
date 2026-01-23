package com.example.edunovel.domain.usecase.material

import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMaterialByIdUseCase(
    private val materialRepository: MaterialRepository
) {
    operator fun invoke(materialId: Long): Flow<Material?> = flow {
        emit(materialRepository.getMaterialById(materialId))
    }
}