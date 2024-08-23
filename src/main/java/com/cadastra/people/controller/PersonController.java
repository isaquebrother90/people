package com.cadastra.people.controller;

import com.cadastra.people.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface PersonController {
    @Operation(
            summary = "Create a new person register",
            description = "Admin user register a new person",
            security = { @SecurityRequirement(name = "bearerAuth")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"content\":[{\"name\":\"Kaio\",\"age\":19,\"address\":{\"cep\":\"13480-323\",\"state\":\"\",\"city\":\"\",\"neighborhood\":\"\",\"street\":\"\"},\"phone\":\"2167678\",\"score\": 150}],\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":1,\"empty\":false}")))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createperson")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String token);

    @Operation(
            summary = "Retrieve person",
            description = "Search person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = PersonDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Integer id);

    @Operation(
            summary = "Update person",
            description = "Admin user update person by id",
            security = { @SecurityRequirement(name = "bearerAuth")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"content\":[{\"name\":\"Kaio\",\"age\":19,\"address\":{\"cep\":\"13480-323\",\"state\":\"\",\"city\":\"\",\"neighborhood\":\"\",\"street\":\"\"},\"phone\":\"2167678\",\"score\": 150}],\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":1,\"empty\":false}")))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/persons/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@Parameter(description = "Person id")  @PathVariable Integer id,
                                                  @RequestBody PersonDTO personDTO,
                                                  @RequestHeader("Authorization") String token);

    @Operation(
            summary = "Patch update person score",
            description = "Admin user patch update person score by id",
            security = { @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/persons/{id}/score")
    public ResponseEntity<PersonDTO> updateScore(@Parameter(description = "Person id") @PathVariable Integer id,
                                                 @RequestParam int newScore,
                                                 @RequestHeader("Authorization") String token);

    @Operation(
            summary = "Logical delete person",
            description = "Admin user delete logically person by id",
            security = { @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/persons/{id}/soft-delete")
    public ResponseEntity<Void> softDeletePerson(@PathVariable Integer id,
                                                 @RequestHeader("Authorization") String token);

    @Operation(
            summary = "Get all persons",
            description = "Retrieve a paginated list of persons based on optional filters",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of persons",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDTO.class),
                                    examples = @ExampleObject(value = "{\"content\":[{\"id\":1,\"name\":\"John Doe\",\"age\":30,\"cep\":\"12345-678\"}],\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":1,\"empty\":false}")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/persons")
    public Page<PersonDTO> getPersons(
            @Parameter(description = "Name of the person to filter by", example = "John Doe") @RequestParam(required = false) String name,
            @Parameter(description = "Age of the person to filter by", example = "30") @RequestParam(required = false) Integer age,
            @Parameter(description = "CEP of the person to filter by", example = "12345-678") @RequestParam(required = false) String cep,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "10") int size);
}
