package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfers;


@Service
public class TransfersSqlDAO implements TransfersDAO{
	private JdbcTemplate jdbcTemplate;

    public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	@Override
	    public void addTransfer(int transferId,int transferTypeId, Integer transferStatusId, Integer accountFrom, Integer accountTo, BigDecimal amount) {
	    	jdbcTemplate.update("INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)"
	    			+ " VALUES (?,?,?,?,?,?)", Integer.class, transferId,transferTypeId, transferStatusId, accountFrom, accountTo, amount);
	    	
	    }
	
	@Override
	public List<Transfers> findAllTransfers() {
		 
        List<Transfers> transfer = new ArrayList<>();
        String sql = "select * from transfers";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
        	Transfers transfers = mapRowToTransfers(results);
            transfer.add(transfers);
        }

        return transfer;
    }
	private Transfers mapRowToTransfers(SqlRowSet rs) {
        Transfers transfer = new Transfers();
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

}