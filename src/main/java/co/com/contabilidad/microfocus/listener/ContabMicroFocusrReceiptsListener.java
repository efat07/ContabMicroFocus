package co.com.contabilidad.microfocus.listener;

import java.text.SimpleDateFormat;
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
import co.com.contabilidad.microfocus.model.ReceiptResponse;

@Component
public class ContabMicroFocusrReceiptsListener {

	
	@JmsListener(destination="jms/contab/receipts/queue")
	public void onMessage( final Message message ) throws JMSException {
		
		System.out.println( "Mensaje: receipts" );
		TextMessage tm = (TextMessage) message;
		System.out.println( tm.getText() );
		
		TypeToken<List<ReceiptResponse>>  token = new TypeToken<List<ReceiptResponse>>(){};
		List<ReceiptResponse> receiptResponses = new Gson().fromJson(tm.getText(), token.getType());
		
		if (receiptResponses != null && !receiptResponses.isEmpty()) {
		
		String asientoContable = "";
		for (ReceiptResponse receiptResponse : receiptResponses) {
			asientoContable = asientoContable + String.valueOf(receiptResponse.getIdOrder());
		}
		
		FIleCreator.createFile("C:/CapaIntegracion/ContabMicroFocus/receipts/inputs/receipts_"+asientoContable+"_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),tm.getText());
	}}
	
}
