package com.merco.dealership.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.merco.dealership.entities.Adm;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.entities.Car;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.CustomerAddress;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.Seller;
import com.merco.dealership.entities.VehicleSpecificDetail;
import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;
import com.merco.dealership.entities.enums.ClientType;
import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.PaymentTerms;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.repositories.AdmRepository;
import com.merco.dealership.repositories.AppointmentRepository;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.repositories.CarRepository;
import com.merco.dealership.repositories.ContractRepository;
import com.merco.dealership.repositories.CustomerAddressRepository;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.InventoryRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.repositories.VehicleSpecificDetailRepository;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {
	private final AdmRepository admRepository;
	private final SellerRepository sellerRepository;
	private final SaleRepository saleRepository;
	private final InventoryRepository inventoryRepository;
	private final ContractRepository contractRepository;
	private final AppointmentRepository appointmentRepository;
	private final CustomerRepository customerRepository;
	private final CustomerAddressRepository customerAddressRepository;
	private final VehicleSpecificDetailRepository vehicleSpecificDetailRepository;
	private final CarRepository carRepository;
	private final BranchRepository branchRepository;
	private final BranchAddressRepository branchAddressRepository;

	public TestConfig(
			AdmRepository admRepository,
			SellerRepository sellerRepository,
			SaleRepository saleRepository,
			InventoryRepository inventoryRepository,
			ContractRepository contractRepository,
			AppointmentRepository appointmentRepository,
			CustomerRepository customerRepository,
			CustomerAddressRepository customerAddressRepository,
			VehicleSpecificDetailRepository vehicleSpecificDetailRepository,
			CarRepository carRepository,
			BranchRepository branchRepository,
			BranchAddressRepository branchAddressRepository
	) {
		this.admRepository = admRepository;
		this.sellerRepository = sellerRepository;
		this.saleRepository = saleRepository;
		this.inventoryRepository = inventoryRepository;
		this.contractRepository = contractRepository;
		this.appointmentRepository = appointmentRepository;
		this.customerRepository = customerRepository;
		this.customerAddressRepository = customerAddressRepository;
		this.vehicleSpecificDetailRepository = vehicleSpecificDetailRepository;
		this.carRepository = carRepository;
		this.branchRepository = branchRepository;
		this.branchAddressRepository = branchAddressRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		admRepository.save(new Adm(null, "Irineu", "(11) 91234-5678", "irineu@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu"));

		BranchAddress branchAddress = new BranchAddress(null, "Other Street", 456,
				"Apt 1A", "Downtown", "Paraná", "PR", "12345-678", "Brazil");
		Branch branch = branchRepository.save(new Branch(null, "Main Branch", branchAddress, "(11) 91212-1212",
				"mainbranch@example.com", "John Doe", "8:00 AM - 6:00 PM", "Dealership", "Active",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 11, 30)));

		var savedSellers = sellerRepository.saveAll(Arrays.asList(
				new Seller(null, "JOHN DOE", "(11) 98765-4321", "john.doe@example.com", "securePass123",
						LocalDate.of(2023, 6, 15), 5000.0, 0.05, "Active"),
				new Seller(null, "JANE SMITH", "(21) 98765-4321", "jane.smith@example.com", "securePass456",
						LocalDate.of(2022, 8, 10), 5500.0, 0.07, "Active"),
				new Seller(null, "ALICE JOHNSON", "(31) 98765-4321", "alice.johnson@example.com",
						"securePass789", LocalDate.of(2021, 4, 5), 6000.0, 0.06, "Inactive"),
				new Seller(null, "BOB MARTINEZ", "(41) 98765-4321", "bob.martinez@example.com",
						"securePass012", LocalDate.of(2020, 12, 20), 4500.0, 0.04, "Active")
		));

		Seller firstSeller = savedSellers.get(0);

		CustomerAddress customerAddress = customerAddressRepository.save(new CustomerAddress(null, "Main Street", 123,
				"Apt 4B", "Downtown", "São Paulo", "SP", "01000-000", "Brazil"));
		Customer customer = customerRepository.save(new Customer(null, "John Doe", "(12) 93456-7890",
				"johndoe@example.com", "1234567890", LocalDate.of(1990, 1, 1),
				LocalDate.now(), ClientType.INDIVIDUAL, true));

		VehicleSpecificDetail vsd1 = new VehicleSpecificDetail(null, "Sunroof with panoramic view");
		VehicleSpecificDetail vsd2 = new VehicleSpecificDetail(null, "Leather seats");
		vehicleSpecificDetailRepository.saveAll(Arrays.asList(vsd1, vsd2));

		Car car = carRepository.save(new Car(null, "Toyota", "Corolla", VehicleType.CAR, VehicleCategory.SEDAN,
				LocalDate.of(2020, 5, 15), "Black", 30000.0, 1500.0, FuelType.GASOLINE, 4,
				"Premium Sound System", 50.0, 140.0, 5, 75000.0, VehicleStatus.NEW, VehicleAvailability.AVAILABLE,
				"Excellent condition, low mileage", LocalDate.of(2024, 11, 30), branch, new HashSet<>(), new HashSet<>(),
				new HashSet<>(), 6, "ABS", 15.0, 12.0, 10.0, 5, "Power Steering", 17, 4, 470.0, "FWD"));
		InventoryItem inventory = inventoryRepository.save(new InventoryItem(null, car, LocalDate.of(2024, 11, 20),
				null, 80000.0, 0.15, "Auto Supplier Ltd.", "ABC-1234", "1HGCM82633A123456"));

		Sale sale = saleRepository.save(new Sale(null, firstSeller, customer, inventory,
				LocalDate.now(), 25000.0, 24000.0, 1000.0, "Credit Card", 12, "RC123456"));
		Contract contract = contractRepository.save(new Contract(null, "CN12345", sale, "Sale",
				LocalDate.now(), LocalDate.now().plusDays(15), 95000.0, PaymentTerms.PIX,
				ContractStatus.SIGNED, "None", "contract_attachment.pdf"));
		sale.setContract(contract);
		saleRepository.save(sale);

		appointmentRepository.save(new Appointment(null, LocalDate.now().plusDays(1), AppointmentType.TEST_DRIVE,
				AppointmentStatus.PENDING, customer, firstSeller));
	}
}