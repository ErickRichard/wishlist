package br.com.personal.wishlist.configuration;

import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.User;
import br.com.personal.wishlist.domain.model.Wishlist;
import br.com.personal.wishlist.domain.repository.ProductRepository;
import br.com.personal.wishlist.domain.repository.UserRepository;
import br.com.personal.wishlist.domain.repository.WishlistRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ChangeLog
public class ChangelogConfig {

    @ChangeSet(order = "", id = "", author = "")
    public void database(UserRepository userRepository, ProductRepository productRepository, WishlistRepository wishlistRepository) {
        var products = productRepository.insert(listOfProducts());
        var users = userRepository.insert(listOfUsers());

        users.forEach(user -> {
            if (!Objects.equals(user.getName(), "teste")) {
                var wishlist = Wishlist.builder()
                        .userId(user.getId())
                        .products(products)
                        .build();
                wishlistRepository.insert(wishlist);
            }
        });
    }

    private List<Product> listOfProducts() {
        List<Product> products = new ArrayList<>();
        products.add(insertProducts("Headset Gamer", "Headset com som surround 7.1 e microfone removível.", new BigDecimal("299.99"), 8));
        products.add(insertProducts("Tablet 10''", "Tablet com tela de 10 polegadas e 64GB de armazenamento.", new BigDecimal("1299.90"), 10));
        products.add(insertProducts("Caixa de Som Bluetooth", "Caixa de som portátil à prova d'água com 12 horas de bateria.", new BigDecimal("199.90"), 25));
        products.add(insertProducts("Carregador Portátil 20.000mAh", "Powerbank com capacidade de 20.000mAh e duas saídas USB.", new BigDecimal("159.90"), 30));
        products.add(insertProducts("Roteador Wi-Fi 6", "Roteador de última geração com suporte ao padrão Wi-Fi 6.", new BigDecimal("599.00"), 5));
        products.add(insertProducts("Cadeira Gamer", "Cadeira ergonômica com ajuste de altura e inclinação.", new BigDecimal("799.90"), 3));
        products.add(insertProducts("SSD 1TB", "SSD com 1TB de capacidade e leitura ultrarrápida.", new BigDecimal("899.90"), 7));
        products.add(insertProducts("Impressora Multifuncional", "Impressora com scanner e conectividade Wi-Fi.", new BigDecimal("429.90"), 4));
        products.add(insertProducts("Console de Videogame", "Console de última geração com 1TB de armazenamento.", new BigDecimal("3999.00"), 6));
        products.add(insertProducts("Placa de Vídeo RTX 3080", "Placa de vídeo para jogos de alta performance.", new BigDecimal("6999.00"), 2));
        products.add(insertProducts("Cooler para Processador", "Cooler com sistema de refrigeração líquida.", new BigDecimal("349.90"), 10));
        products.add(insertProducts("Memória RAM 16GB DDR4", "Módulo de memória DDR4 de 16GB para alto desempenho.", new BigDecimal("499.90"), 12));
        products.add(insertProducts("Fonte de Alimentação 750W", "Fonte de alimentação modular com 80 Plus Gold.", new BigDecimal("699.00"), 5));
        products.add(insertProducts("Volante para Simulador", "Volante para simuladores com force feedback.", new BigDecimal("1399.90"), 3));
        products.add(insertProducts("Joystick", "Controle para jogos de voo e simuladores.", new BigDecimal("299.90"), 8));
        products.add(insertProducts("Webcam Full HD", "Webcam com resolução 1080p e foco automático.", new BigDecimal("199.90"), 15));
        products.add(insertProducts("Placa-Mãe ATX", "Placa-mãe com suporte a processadores Intel de 10ª geração.", new BigDecimal("999.00"), 4));
        products.add(insertProducts("Hub USB 3.0", "Hub com 4 portas USB 3.0.", new BigDecimal("89.90"), 20));
        products.add(insertProducts("Microfone Condensador", "Microfone profissional para gravações e streaming.", new BigDecimal("499.90"), 6));
        products.add(insertProducts("Leitor de Cartão SD", "Leitor de cartões SD e microSD com USB 3.0.", new BigDecimal("49.90"), 50));
        products.add(insertProducts("Adaptador USB-C para HDMI", "Adaptador para conexão de dispositivos USB-C a monitores HDMI.", new BigDecimal("99.90"), 40));
        products.add(insertProducts("Balança Inteligente", "Balança digital com análise de composição corporal.", new BigDecimal("249.90"), 8));
        products.add(insertProducts("Câmera de Segurança Wi-Fi", "Câmera de vigilância com visão noturna e detecção de movimento.", new BigDecimal("349.90"), 10));
        products.add(insertProducts("Controle Universal", "Controle remoto universal para TV e dispositivos eletrônicos.", new BigDecimal("69.90"), 20));
        products.add(insertProducts("Purificador de Água", "Purificador de água com filtro de carvão ativado.", new BigDecimal("799.90"), 5));
        products.add(insertProducts("TV 55'' 4K", "Smart TV 4K com 55 polegadas e HDR.", new BigDecimal("3499.00"), 2));
        products.add(insertProducts("Soundbar", "Barra de som com subwoofer e conectividade Bluetooth.", new BigDecimal("799.00"), 7));
        products.add(insertProducts("Switch de Rede 8 Portas", "Switch Gigabit Ethernet com 8 portas.", new BigDecimal("199.90"), 15));
        products.add(insertProducts("Repetidor de Sinal Wi-Fi", "Repetidor para ampliação da cobertura de Wi-Fi.", new BigDecimal("149.90"), 12));
        products.add(insertProducts("Câmera GoPro", "Câmera de ação à prova d'água com estabilização de imagem.", new BigDecimal("1999.00"), 3));
        return products;
    }

    private List<User> listOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(insertUsers("wishlist 1", "person1@wishlist.com.br", encodedPassword("123")));
        users.add(insertUsers("wishlist 2", "person2@wishlist.com.br", encodedPassword("1234")));
        users.add(insertUsers("wishlist 3", "person3@wishlist.com.br", encodedPassword("12345")));
        users.add(insertUsers("teste", "teste@wishlist.com.br", encodedPassword("123456")));
        return users;
    }

    private Product insertProducts(String name, String description, BigDecimal price, Integer quantity) {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();
    }

    private User insertUsers(String name, String email, String password) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    private String encodedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
