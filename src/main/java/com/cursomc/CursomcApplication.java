package com.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.Pagamento;
import com.cursomc.domain.PagamentoComCartao;
import com.cursomc.domain.PagamentoComCheque;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.repositories.EstadoRepository;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*Categorias e Produtos*/
		Categoria cat1  = new Categoria(null, "Informática");
		Categoria cat2  = new Categoria(null, "Escritório");
		Categoria cat3  = new Categoria(null, "Jogos");
		Categoria cat4  = new Categoria(null, "Eletrónica");
		Categoria cat5  = new Categoria(null, "Jardinagem");
		Categoria cat6  = new Categoria(null, "Decoração");
		Categoria cat7  = new Categoria(null, "Perfumaria");
		
		Produto prd1 = new Produto(null, "Computador", 2000.00 );
		Produto prd2 = new Produto(null, "Impressora", 800.00 );
		Produto prd3 = new Produto(null, "Mouse", 80.00 );
		
		cat1.getProdutos().addAll(Arrays.asList(prd1, prd2, prd3));
		cat2.getProdutos().addAll(Arrays.asList(prd2));
		
		prd1.getCategorias().addAll(Arrays.asList(cat1));
		prd2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prd3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(prd1, prd2, prd3));
		
		/*Distritos e Cidades*/
		Estado est1 = new Estado(null, "Braga");
		Estado est2 = new Estado(null, "Porto");
		
		Cidade c1 = new Cidade(null, "Esposende", est1);
		Cidade c2 = new Cidade(null, "Póvoa de Varzim", est2);
		Cidade c3 = new Cidade(null, "Maia", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		/*Clientes e endereços*/
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36398798798", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("987987987", "321654987"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "98798797", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		/*Pedidos e Pagamentos*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Pedido ped1 = new Pedido(null, sdf.parse("04/08/2018 10:38:45"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2018 10:38:55"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.PAGO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComCheque(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/11/2018 09:38:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		//Items pedido
		ItemPedido ip1 = new ItemPedido(ped1, prd1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prd3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prd2, 100.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(ip1, ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));
		
		prd1.getItems().addAll(Arrays.asList(ip1));
		prd2.getItems().addAll(Arrays.asList(ip3));
		prd3.getItems().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}
}
