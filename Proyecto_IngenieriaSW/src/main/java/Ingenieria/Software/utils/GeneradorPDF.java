package Ingenieria.Software.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Ingenieria.Software.model.Producto;

public class GeneradorPDF {

	private static Logger logger = LoggerFactory.getLogger(GeneradorPDF.class);
	
	
	
	public static ByteArrayInputStream customerPDFReport(List<Producto> customers) throws IOException {
		
	Document document = new Document();
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	try
	{
		PdfWriter.getInstance(document,out);
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
		Paragraph para = new Paragraph("Productos sugeridos",font);
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
		document.add(Chunk.NEWLINE);
		
		PdfPTable table = new PdfPTable(3);
		 
		Stream.of("Nombre del Producto","descripcion","Precio").forEach(headerTitle ->{
			PdfPCell header = new PdfPCell();
			Font headfont = FontFactory.getFont(FontFactory.COURIER_BOLD);
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(headerTitle,headfont));
			table.addCell(header);
			
		});
			
			
			for(Producto customer : customers) {
				
				PdfPCell idCell = new PdfPCell (new Phrase(customer.getNombre()));
				idCell.setPaddingLeft(3);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);
			
			
		
				
				PdfPCell descrpcion = new PdfPCell (new Phrase(customer.getDescripcion()));
				idCell.setPaddingLeft(3);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(descrpcion);
			
		
		
		
				
				PdfPCell precio = new PdfPCell (new Phrase(String.valueOf(customer.getPrecio())));
				idCell.setPaddingLeft(3);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(precio);
			}
		document.add(table);
	document.close();
	
	}catch(DocumentException e) {
		logger.error(e.toString());
		
	}
	try {
		FileOutputStream fos = new FileOutputStream ("D:\\OneDrive\\Documentos\\GitHub\\PROYECTO-ISS\\Proyecto_IngenieriaSW\\src\\main\\resources\\static\\pdf\\10.pdf", true);
		
		fos.write(out.toByteArray());
        fos.close();
		
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	return new ByteArrayInputStream (out.toByteArray());
	}
	
}
