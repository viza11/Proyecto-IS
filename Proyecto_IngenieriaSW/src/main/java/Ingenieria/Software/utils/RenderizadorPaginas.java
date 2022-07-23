package Ingenieria.Software.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class RenderizadorPaginas <T> {
	private String url;
	private Page<T> page;
	private int totalPags;
	private int nElemenPorPag;
	private int pagAc;
	private List<ElementosPagina> paginas;
	
	public RenderizadorPaginas(String url, Page<T> page){
		this.url=url;
		this.page=page;
		this.paginas = new ArrayList<ElementosPagina>();
		
		totalPags = page.getTotalPages();
		nElemenPorPag=page.getSize();
		pagAc=page.getNumber()+1;
		
		int desde,hasta;
		desde=1;
		hasta=totalPags;
		
		for(int i=0;i<hasta;i++){
			paginas.add(new ElementosPagina(desde +i,pagAc==desde+i));
		}
		
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalPags() {
		return totalPags;
	}

	public void setTotalPags(int totalPags) {
		this.totalPags = totalPags;
	}

	public int getPagAc() {
		return pagAc;
	}

	public void setPagAc(int pagAc) {
		this.pagAc = pagAc;
	}

	public List<ElementosPagina> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<ElementosPagina> paginas) {
		this.paginas = paginas;
	}
}
