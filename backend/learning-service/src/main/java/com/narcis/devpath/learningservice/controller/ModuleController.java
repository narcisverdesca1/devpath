package com.narcis.devpath.learningservice.controller;

import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.exception.ApiError;
import com.narcis.devpath.learningservice.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Module", description = "Module management APIs")
public class ModuleController {

    private final ModuleService moduleService;


    @PostMapping("/courses/{courseId}/modules")
    @Operation(summary = "Create a new module for a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ModuleResponseDto createModule(@PathVariable Long courseId,
                                          @Valid @RequestBody ModuleRequestDto moduleRequestDto) {
        return moduleService.createModule(courseId, moduleRequestDto);
    }

    @GetMapping("/courses/{courseId}/modules")
    @Operation(summary = "Get all modules for a course")
    @ApiResponse(responseCode = "200", description = "Modules retrieved successfully")
    public List<ModuleResponseDto> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.findModulesByCourseId(courseId);
    }

    @GetMapping("/modules/{id}")
    @Operation(summary = "Get module by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Module not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ModuleResponseDto getModuleById(@PathVariable Long id) {
        return moduleService.findModuleById(id);
    }

    @PutMapping("/modules/{id}")
    @Operation(summary = "Update module by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Module not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ModuleResponseDto updateModule(@PathVariable Long id,
                               @Valid @RequestBody ModuleRequestDto moduleRequestDto) {
        return moduleService.updateModuleById(id, moduleRequestDto);
    }

    @DeleteMapping("/modules/{id}")
    @Operation(summary = "Delete module by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Module not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModuleById(id);
    }
}
