package com.merco.dealership.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.merco.dealership.entities.Adm;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.CustomerAddress;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.Seller;
import com.merco.dealership.entities.Vehicle;
import com.merco.dealership.entities.VehicleSpecificDetail;
import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;
import com.merco.dealership.entities.enums.ClientType;
import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.PaymentTerms;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.repositories.AdmRepository;
import com.merco.dealership.repositories.AppointmentRepository;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.repositories.ContractRepository;
import com.merco.dealership.repositories.CustomerAddressRepository;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.InventoryRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.repositories.VehicleSpecificDetailRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private AdmRepository admRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAddressRepository customerAddressRepository;

	@Autowired
	private VehicleSpecificDetailRepository vehicleSpecificDetailRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private BranchAddressRepository branchAddressRepository;

	@Override
	public void run(String... args) throws Exception {
		Adm adm = new Adm(null, "Irineu", "(11) 91234-5678", "irineu@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu");

		Seller seller1 = new Seller(null, "JOHN DOE", "(11) 98765-4321", "john.doe@example.com", "securePass123",
				LocalDate.of(2023, 6, 15), 5000.0, 0.05, "Active");
		Seller seller2 = new Seller(null, "JANE SMITH", "(21) 98765-4321", "jane.smith@example.com", "securePass456",
				LocalDate.of(2022, 8, 10), 5500.0, 0.07, "Active");
		Seller seller3 = new Seller(null, "ALICE JOHNSON", "(31) 98765-4321", "alice.johnson@example.com",
				"securePass789", LocalDate.of(2021, 4, 5), 6000.0, 0.06, "Inactive");
		Seller seller4 = new Seller(null, "BOB MARTINEZ", "(41) 98765-4321", "bob.martinez@example.com",
				"securePass012", LocalDate.of(2020, 12, 20), 4500.0, 0.04, "Active");
		CustomerAddress customerAddress1 = new CustomerAddress(null, "Main Street", 123, "Apt 4B", "Downtown",
				"São Paulo", "SP", "01000-000", "Brazil");

		Customer customer1 = new Customer(null, "John Doe", "(12) 93456-7890", "johndoe@example.com", "1234567890",
				LocalDate.of(1990, 1, 1), LocalDate.now(), ClientType.INDIVIDUAL, true);

		Vehicle vehicle1 = new Vehicle(null, "Toyota", "Corolla", VehicleType.CAR, VehicleCategory.SEDAN,
				LocalDate.of(2020, 5, 15), "Black", 35000.0, FuelType.GASOLINE, TransmissionType.AUTOMATIC, 80000.0,
				VehicleStatus.SEMINOVO, VehicleAvailability.AVAILABLE, "A well-maintained sedan",
				LocalDate.of(2023, 11, 29), "Santos, SP");

		VehicleSpecificDetail vehicleSpecificDetail1 = new VehicleSpecificDetail(null, "Sunroof with panoramic view");
		VehicleSpecificDetail vehicleSpecificDetail2 = new VehicleSpecificDetail(null, "Leather seats");
		VehicleSpecificDetail vehicleSpecificDetail3 = new VehicleSpecificDetail(null, "Premium sound system");
		VehicleSpecificDetail vehicleSpecificDetail4 = new VehicleSpecificDetail(null, "Adaptive cruise control");
		VehicleSpecificDetail vehicleSpecificDetail5 = new VehicleSpecificDetail(null, "Automatic parking assistance");

		InventoryItem inventoryItem1 = new InventoryItem(null, vehicle1, LocalDate.of(2024, 11, 20), null, 80000.0,
				0.15, "Auto Supplier Ltd.", "ABC-1234", "1HGCM82633A123456");

		Sale sale1 = new Sale(null, seller1, customer1, inventoryItem1, LocalDate.of(1990, 1, 1), 25000.0, 24000.0,
				1000.0, "Credit Card", 12, "RC123456");

		Contract contract1 = new Contract(null, "CN12345", sale1, "Sale", LocalDate.of(2024, 11, 29),
				LocalDate.of(2024, 12, 15), 95000.0, PaymentTerms.PIX, ContractStatus.SIGNED, "None",
				"contract_attachment.pdf");

		Appointment appointment1 = new Appointment(null, LocalDate.of(2024, 12, 1), AppointmentType.TEST_DRIVE,
				AppointmentStatus.PENDING, customer1, seller1);

		BranchAddress branchAddress1 = new BranchAddress(null, "Other Street", 456, "Apt 1A", "Downtown", "Paraná",
				"PR", "12345-678", "Brazil");

		Branch branch1 = new Branch(null, "Main Branch", branchAddress1, "(11) 91212-1212", "mainbranch@example.com",
				"John Doe", "8:00 AM - 6:00 PM", "Dealership", "Active", LocalDate.of(2023, 1, 1),
				LocalDate.of(2023, 11, 30));

		admRepository.save(adm);
		sellerRepository.saveAll(Arrays.asList(seller1, seller2, seller3, seller4));
		customerAddressRepository.save(customerAddress1);
		customerRepository.save(customer1);
		vehicleRepository.save(vehicle1);
		vehicleSpecificDetailRepository.saveAll(Arrays.asList(vehicleSpecificDetail1, vehicleSpecificDetail2,
				vehicleSpecificDetail3, vehicleSpecificDetail4, vehicleSpecificDetail5));
		inventoryRepository.save(inventoryItem1);
		saleRepository.save(sale1);
		contractRepository.save(contract1);
		sale1.setContract(contract1);
		saleRepository.save(sale1);
		appointmentRepository.save(appointment1);
		branchAddressRepository.save(branchAddress1);
		branchRepository.save(branch1);
	}

}