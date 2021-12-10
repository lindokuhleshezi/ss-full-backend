/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import za.ac.ss.entities.RoadAccidentFund;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.RAFService;

/**
 *
 * @author lindokuhle
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/raf")
public class RAFController {

    @Autowired private RAFService roadAccidentFundService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public List<RoadAccidentFund> findAll() {
        return this.roadAccidentFundService.findAll();
    }

    @GetMapping("/id/{id}")
    // @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<RoadAccidentFund> findById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        RoadAccidentFund roadAccidentFund = this.roadAccidentFundService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found for this id :: " + id));
        return new ResponseEntity<>(roadAccidentFund, HttpStatus.OK);
    }

    
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public void save(@Valid @RequestBody RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException {
        this.roadAccidentFundService.save(roadAccidentFund);
    }
    
    @PutMapping("/update/{id}")
    // @PreAuthorize("hasAuthority('create')")
    public ResponseEntity<RoadAccidentFund> update(@PathVariable(value = "id") Long id, @Valid @RequestBody RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(this.roadAccidentFundService.update(id, roadAccidentFund));
    }

    @DeleteMapping("/delete/{id}")
    // @PreAuthorize("hasAuthority('create')")
    public void delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        this.roadAccidentFundService.delete(id);
    }

    @GetMapping("/daysleft/{id}")
    // @PreAuthorize("hasAuthority('read')")
    public Long daysLeft(@PathVariable Long id) throws ResourceNotFoundException {
    	return this.roadAccidentFundService.daysLeft(id); 
    }
    
    @GetMapping("/search/{referenceno}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoadAccidentFund>> findByReferenceNo(@PathVariable(value = "referenceno") String referenceno) throws ResourceNotFoundException {
        List<RoadAccidentFund> roadAccidentFund = this.roadAccidentFundService.findByReferenceNo(referenceno);
        return ResponseEntity.ok().body(roadAccidentFund);
    }
    
    @GetMapping("/username/{username}")
    // @PreAuthorize("hasAuthority('read')")
    public List<RoadAccidentFund> findByUsername(@PathVariable String username) throws ResourceNotFoundException  {
        return this.roadAccidentFundService.findByUsername(username);
    }
}
