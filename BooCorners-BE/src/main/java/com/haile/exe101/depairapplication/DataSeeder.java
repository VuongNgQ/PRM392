package com.haile.exe101.depairapplication;

import com.haile.exe101.depairapplication.models.entity.Category;
import com.haile.exe101.depairapplication.models.entity.Product;
import com.haile.exe101.depairapplication.models.entity.Role;
import com.haile.exe101.depairapplication.models.entity.User;
import com.haile.exe101.depairapplication.models.enums.ERole;
import com.haile.exe101.depairapplication.repositories.CategoryRepository;
import com.haile.exe101.depairapplication.repositories.ProductRepository;
import com.haile.exe101.depairapplication.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(RoleRepository roleRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            List<Category> categories = new ArrayList<>();
            categories.add(new Category(null, "Skincare"));
            categories.add(new Category(null, "Makeup"));
            if (categoryRepository.count() == 0) {
//                categories.add(new Category(null, "Hair care"));
//                categories.add(new Category(null, "Fragrance"));

                // Save categories to the database
                categoryRepository.saveAll(categories);
            }

            List<Product> products = new ArrayList<>();
            if (productRepository.count() == 0) {
                products.add(new Product(null, "Tinh chất chống lão hoá The Ordinary Multi-Peptide + HA (Buffet)", "The Ordinary", 20, categories.get(0), 390000, "Tổ hợp từ 11 loại Amino Acid giúp làn da được trẻ hóa và tăng cường tái kết cấu da\n" +
                        "Phục hồi và tái tạo da, làm căng mọng da, giảm nếp nhăn, cấp ẩm chuyên sâu\n" +
                        "Dung tích : 30 ml\n" +
                        "Xuất xứ : Canada", "https://imgur.com/FFOwqFN", false, null, null));
                products.add(new Product(null, "Gel ngăn ngừa sẹo Obagi Scar Refining Gel", "Obagi", 20, categories.get(0), 590000, "Thúc đẩy quá trình lành thương, giảm viêm.\n" +
                        "Làm dịu da, giảm ngứa, giảm đỏ.\n" +
                        "Cải thiện vẻ ngoài thẩm mỹ của sẹo.\n" +
                        "Giảm thiểu sẹo xấu, sẹo thâm.", "https://zomedical.vn/wp-content/uploads/2022/08/Scar-Refining-Gel-3-1-500x500.jpg", false, null, null));
                products.add(new Product(null, "Kem Dưỡng Ẩm Obagi Hydrate Cấp Ẩm Suốt 8 giờ", "Obagi", 20, categories.get(0), 490000, "Kem Dưỡng Giữ Nước Cấp Ẩm Suốt 8 Giờ Obagi Hydrate Facial Moisturizer", "https://www.obagi.vn/cdn/shop/products/2022Q4WEBSHOOT_Hydrate_PDPhero_1260x1260_72dpi.jpg?v=1692976866", false, null, null));
                products.add(new Product(null, "Kem dưỡng mắt cao cấp Kate Somerville Lifting Eye Cream", "Kate Somerville", 20, categories.get(0), 590000, "Kem dưỡng mắt dòng cao cấp nhất của nhà Kate- somerville. fullsize tầm 4.5tr cho 15ml \n" +
                        "Các thành phần chính:\n" +
                        "- Triple-Peptide Complex: hạn chế các nếp nhăn và vết chân chim, tăng độ đàn hồi và săn chắc của vùng da mắt.\n" +
                        "- Rong biển và tảo đỏ: Giảm thâm quầng và bọng mắt.\n" +
                        "- Vitamin C: Làm sáng và săn chắc vùng da mắt\n" +
                        "\n" +
                        "- Triple-Peptide Complex: Relaxes the look of lines, wrinkles, and crow’s feet and visibly increases firmness and elasticity.\n" +
                        "- Brown and Red Algae: Visibly diminishes dark under-eye circles and puffiness.\n" +
                        "- Vitamin C: Brightens and firms the appearance of skin.", "https://i.imgur.com/Xp2i2IV.jpeg", false, null, null));
                products.add(new Product(null, "KateCeuticals™ Resurfacing Overnight Peel", "Kate Somerville", 20, categories.get(0), 530000, "Giảm sự xuất hiện của đường nhăn và nếp nhăn, biến đổi kết cấu da và giảm rõ rệt các đốm đen", "https://i.imgur.com/lPXwhkt.jpeg", false, null, null));
                products.add(new Product(null, "Tinh chất dưỡng ẩm KATE SOMERVILLE HydraKate Recharging Serum with Hyaluronic Acid", "Kate Somerville", 20, categories.get(0), 570000, "Serum mỏng nhẹ thấm nhanh\n" +
                        "Sử dụng công nghệ AquaPort Technology để cấp ẩm cho da, đưa hyaluronic acid thấm sâu dưỡng ẩm cả ngày.\n" +
                        "Blue Light-Activated Algae Extract giúp chống lão hoá, giảm nếp nhăn đốm nâu trên da", "https://i.imgur.com/vbQpOy0.jpeg", false, null, null));
                products.add(new Product(null, "Gold Bond - Kem dưỡng cổ và ngực Gold Bond Ultimate Neck & Chest Firming Cream 56G", "GOLD BOND", 20, categories.get(0), 250000, "Kem dưỡng cho vùng cổ & ngực đây cả nhà ơi" +
                        "\n" +
                        "- Kem làm mờ rãnh nhăn cổ & ngực giúp trẻ hoá da cổ & ngực 97% sau 2 tuần sử dụng\n" +
                        "- Làm sáng  mịn & tạo đàn hồi cho vùng da cổ & ngực\n" +
                        "- Thoa kem mỗi tối theo hướng đi lên, từ trong ra ngoài", "https://i5.walmartimages.com/asr/61ec8a3f-38e6-4503-a91b-a842c80e7d3b.c66e94dd57100c39a393d223d41ced29.jpeg", false, null, null));
                products.add(new Product(null, "Kem Dưỡng Chống Lão Hoá Fresh Black Tea Corset Cream Firming Moisturizer", "Fresh", 20, categories.get(0), 590000, "50ml\n" +
                        "\n" +
                        "- Với chiết xuất từ men trà đen, lá trà đen, lá trái blackberry cùng với lá từ trái nhãn tạo nên phức hợp dưỡng chất trong việc làm săn chắc da, ngăn ngừa và hạn chế tối đa các nếp nhăn cho vùng mặt và cổ.\n" +
                        "\n" +
                        "- Lychee seed và Polysaccharides giúp làm mờ và ngăn chặn sự xuất hiện của rãnh nhăn, duy trì độ săn chắc của da, làm săn và nâng cơ mặt, bảo vệ độ đàn hồi cho các tế bào, giúp trẻ hóa đáng kể.\n" +
                        "\n" +
                        "- Tinh chất từ lá dâu đen giúp có tác dụng dưỡng mịn và cải thiện độ căng bóng của da, mang lại làn da tươi trẻ và căng mịn.\n" +
                        "\n" +
                        "- Chiết xuất từ quả Gogi đỏ mọng nhiều dưỡng chất hiệu quả trong việc làm săn và nâng cơ mặt. bảo vệ độ đàn hồi cho các tế bào, giúp trẻ hóa làn da, làm chậm lão hóa.\n" +
                        "\n" +
                        "Hướng dẫn sử dụng\n" +
                        "\n" +
                        "• Sử dụng đều dặn ngày 2 lần cho vùng da mặt và cổ ngay sau bước làm sạch hoạc serum.", "https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/02/kem-duong-san-chac-da-fresh-black-tea-firming-corset-cream-50ml-620b294b606b7-15022022111715.jpg", false, null, null));
                products.add(new Product(null, "Kem dưỡng đêm nâng cơ da mặt và cổ Estée Lauder Resilience Multi-Effect Night Tri-Peptide Face and Neck Crème", "Estée Lauder", 20, categories.get(0), 1090000, "- Bổ sung độ ẩm cao  cho da mềm mại, chống lại mất nước qua đêm\n" +
                        "\n" +
                        "- Tăng độ săn chắc, căng nảy và đàn hồi để làm mờ vết nhăn.\n" +
                        "\n" +
                        "- Chống lại hiện tượng chảy xệ và chùng nhão trên da.\n" +
                        "\n" +
                        "- Nâng cơ cho khuôn mặt thon gọn và săn chắc.\n" +
                        "\n" +
                        "- Xóa các vết thâm, tái tạo phục hồi da.\n" +
                        "\n" +
                        "- Chống lão hóa da.\n" +
                        "\n" +
                        "- Tăng cường dưỡng chất hỗ trợ phục hồi da.\n" +
                        "\n" +
                        "- Cung cấp da trong suốt đêm dài để sáng hôm sau thức dậy da trở nên tươi sáng, khỏe mạnh.", "https://m.esteelauder.com/media/export/cms/products/640x640/el_sku_RRLM01_640x640_0.jpg", false, null, null));
                products.add(new Product(null, "Kem Dưỡng Ngày Nâng Cơ Da Estee Lauder Resilience Multi-Effect Tri-Peptide Face And Neck Creme Spf15 50Ml", "Estée Lauder", 20, categories.get(0), 1090000, "- Công dụng chính: Kem dưỡng ẩm chuyên sâu cả ngày dài, nuôi dưỡng làn da căng mọng và tràn đầy sức sống.\n" +
                        "\n" +
                        "- Loại da sử dụng tốt nhất: Mặt & cổ\n" +
                        "\n" +
                        "- Thành phần chính: Tảo biển, Dưa gang, Dầu bơ\n" +
                        "\n" +
                        "(*) Hiệu quả sử dụng: Làn da trở nên săn chắc, căng mịn sau 4 tuần sử dụng đều đặn.", "https://image.hsv-tech.io/1987x0/bbx/products/c41fa3c9-98ad-4429-b966-a5bbfe2e4e83.webp", false, null, null));
                products.add(new Product(null, "Skin Fetish: Divine Bronzer", "PAT McGRATH LABS", 50, categories.get(1), 390000, "Giới thiệu Skin Fetish: Divine Bronzer - công cụ tối ưu để điêu khắc và xác định vẻ đẹp rạng rỡ bằng sức mạnh của mặt trời. Phấn tạo khối dạng bột nhung siêu nhỏ này mềm mại sang trọng và có độ sáng lấp lánh để mang lại làn da rám nắng kéo dài cả ngày. Hoàn hảo cho mọi tông màu da, màu sắc trong Skin Fetish: Divine Bronzer được tuyển chọn cẩn thận để cân bằng các tông màu đỏ, vàng và trung tính. Nó cung cấp một lớp hoàn thiện ánh ngọc trai và bảy lớp hoàn thiện mờ có thể được tùy chỉnh để đạt được mức độ tỏa nhiệt cao cấp và đường nét khuôn mặt như mong muốn của bạn. Với công thức giúp bạn trông rạng rỡ từ sáng đến tối, Divine Bronzer có sự pha trộn được tuyển chọn giữa các loại bột hút dầu và ngọc trai vàng vi tinh chế giúp làn da của bạn trở nên thần thánh vô tận. Loại phấn làm mịn cảm giác, vương miện cao cấp này sử dụng loại phấn trong mờ, mềm mại để đảm bảo một lớp trang điểm hoàn hảo giúp làn da của bạn không bị bong tróc hoặc bị bong tróc. Mở khóa làn da đẹp, rám nắng với Divine Bronzer mềm mại gợi cảm này và trải nghiệm công nghệ chống nắng tối ưu.", "https://www.patmcgrath.com/cdn/shop/products/PMG_PDP_DIVINE_BRONZER_Bronze_Nirvana_Open_1.jpg?v=1679088712&width=1200", false, null, null));

                productRepository.saveAll(products);
            }

            List<Role> roles = new ArrayList<>();
            if (roleRepository.count() == 0) {
                roles.add(new Role(null, ERole.ROLE_USER));
                roles.add(new Role(null, ERole.ROLE_ADMIN));
                roles.add(new Role(null, ERole.ROLE_STAFF));

                roleRepository.saveAll(roles);
            }

            System.out.println("Data seeding completed!");
        };
    }
}
