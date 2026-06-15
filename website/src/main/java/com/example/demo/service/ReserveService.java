package com.example.demo.service;

import com.example.demo.model.Reserve;
import com.example.demo.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    public List<Reserve> findAll() {
        return reserveRepository.findAllByOrderByDateDesc();
    }

    public Optional<Reserve> findById(Long id) {
        return reserveRepository.findById(id);
    }

    public Reserve save(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    public void deleteById(Long id) {
        reserveRepository.deleteById(id);
    }
}