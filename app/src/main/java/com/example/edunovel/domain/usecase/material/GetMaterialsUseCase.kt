package com.example.edunovel.domain.usecase.material

import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow

class GetMaterialsUseCase(
    private val materialRepository: MaterialRepository
) {
    operator fun invoke(): Flow<List<Material>> {
        return materialRepository.getAllMaterials()
    }
}