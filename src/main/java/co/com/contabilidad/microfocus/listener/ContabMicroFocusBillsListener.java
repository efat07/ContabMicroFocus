package co.com.contabilidad.microfocus.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.com.contabilidad.microfocus.file.FIleCreator;
import co.com.contabilidad.microfocus.model.BillResponse;

@Component
public class ContabMicroFocusBillsListener {

	@JmsListener(destination="jms/contab/bills/queue")
	public void onMessage( final Message message ) throws JMSException {
		
		System.out.println( "Mensaje: bills" );
		TextMessage tm = (TextMessage) message;
		System.out.println( tm.getText() );
		
		TypeToken<List<BillResponse>>  token = new TypeToken<List<BillResponse>>(){};
		List<BillResponse> billResponses = new Gson().fromJson(tm.getText(), token.getType());
		
		if (billResponses != null && !billResponses.isEmpty()) {
		
		String asientoContable = "";
		for (BillResponse billResponse : billResponses) {
			asientoContable = asientoContable + String.valueOf(billResponse.getIdOrder());
		}
		
		FIleCreator.createFile("C:/CapaIntegracion/ContabMicroFocus/bills/inputs/bills_"+asientoContable+"_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),tm.getText());
		}
	}
	
}
