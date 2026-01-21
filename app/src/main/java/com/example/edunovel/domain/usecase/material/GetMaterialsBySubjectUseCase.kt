package com.example.edunovel.domain.usecase.material

import com.example.edunovel.domain.model.Material
import com.example.edunovel.domain.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow

class GetMaterialsBySubjectUseCase(
    private val materialRepository: MaterialRepository
) {
    operator fun invoke(subject: String): Flow<List<Material>> {
        return materialRepository.getMaterialsBySubject(subject)
    }
}