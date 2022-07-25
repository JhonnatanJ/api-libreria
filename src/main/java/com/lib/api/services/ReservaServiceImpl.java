package com.lib.api.services;

import com.lib.api.entities.DetalleReserva;
import com.lib.api.entities.Reserva;
import com.lib.api.repositories.CuentaRepository;
import com.lib.api.repositories.LibroRepository;
import com.lib.api.repositories.ReservaRepository;
import com.lib.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<Reserva> findAll() throws Exception {
        try{
            return reservaRepository.findAll();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva findById(Long id) throws Exception {
        try{
            Optional<Reserva> optionalEntitie = reservaRepository.findById(id);
            return optionalEntitie.get();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva save(Reserva entity) throws Exception {
        try {
            DecimalFormat df = new DecimalFormat("###.##");

            entity.setFechaCreacion(LocalDate.now());
            entity.setCuenta(cuentaRepository.findById(entity.getCuenta().getIdCuenta()).get());

            if(usuarioRepository.findById(entity.getUsuario().getCi()).isPresent()){
                entity.setUsuario(usuarioRepository.findById(entity.getUsuario().getCi()).get());
            }

            double valor_total = 0;
            for(DetalleReserva detalleReserva: entity.getDetalleReservas()){
                detalleReserva.setLibro(libroRepository.findById(detalleReserva.getLibro().getISBN()).get());
                detalleReserva.setSubtotal(Math.round((detalleReserva.getLibro().getPrecioUnitario() * detalleReserva.getCantidad())*100.0)/100.0);
                valor_total += detalleReserva.getSubtotal();
            }
            valor_total = Math.round(valor_total*100.0)/100.0;
            double saldo = Math.round((valor_total - entity.getAbono())*100.0)/100.0;
            entity.setValorTotal(valor_total);
            entity.setSaldo(saldo);


            entity = reservaRepository.save(entity);
            return entity;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva update(Long id, Reserva entity) throws Exception {
        try {
            if (reservaRepository.existsById(id)) {
                save(entity);
                return entity;
            } else {
                return null;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            if(reservaRepository.existsById(id)){
                reservaRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}