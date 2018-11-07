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
import co.com.contabilidad.microfocus.model.InvoiceResponse;

@Component
public class ContabMicroFocusInvoicesListener {

	
	@JmsListener(destination="jms/contab/invoices/queue")
	public void onMessage( final Message message ) throws JMSException {
		
		System.out.println( "Mensaje: invoices" );
		TextMessage tm = (TextMessage) message;
		System.out.println( tm.getText() );
		
		TypeToken<List<InvoiceResponse>>  token = new TypeToken<List<InvoiceResponse>>(){};
		List<InvoiceResponse> invoiceResponses = new Gson().fromJson(tm.getText(), token.getType());
		
		if (invoiceResponses != null && !invoiceResponses.isEmpty()) {
		
		String asientoContable = "";
		for (InvoiceResponse invoiceResponse : invoiceResponses) {
			asientoContable = asientoContable + String.valueOf(invoiceResponse.getIdOrder());
		}
		
		FIleCreator.createFile("C:/CapaIntegracion/ContabMicroFocus/invoices/inputs/invoices_"+asientoContable+"_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),tm.getText());
	}}
	
}
