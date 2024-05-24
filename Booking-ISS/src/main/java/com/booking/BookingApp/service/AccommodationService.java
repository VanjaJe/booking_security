package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.PricelistItemDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.repository.TimeSlotRepository;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import com.booking.BookingApp.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccommodationService implements IAccommodationService {

    @Value("${image-path}")
    private String imagesDirPath;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    AvailabilityService availabilityService;

    @Override
    public double calculatePriceForAccommodation(Long id, int guestNumber, Date begin, Date end) {
        Accommodation accommodation=findOne(id);
        if(accommodation==null){
            return 0.0;
        }

        double price = 0;

        long timeDifference=end.getTime()-begin.getTime();
        double days=Math.floor(timeDifference / (1000 * 60 * 60 * 24));
        int counter=0;

        boolean pricePerGuest = accommodation.isPricePerGuest();
        Collection<PricelistItem> priceList = accommodation.getPriceList();

        if (priceList != null) {
            for (PricelistItem item : priceList) {
                long intersectionStart = Math.max(begin.getTime(), java.sql.Date.valueOf(item.getTimeSlot().getStartDate()).getTime());
                long intersectionEnd = Math.min(end.getTime(), java.sql.Date.valueOf(item.getTimeSlot().getEndDate()).getTime());

                if (intersectionStart < intersectionEnd) {
                    long daysInIntersection = (intersectionEnd - intersectionStart) / (1000 * 60 * 60 * 24);
                    price +=daysInIntersection * item.getPrice();
                    counter+=daysInIntersection;
                }
            }
        }
        if(counter<days){
            return 0;
        }
        if(pricePerGuest){
           return price*guestNumber;
        }
        return price;
    }

    @Override
    public Accommodation accept(Accommodation accommodation) {
        accommodation.setStatus(AccommodationStatus.ACCEPTED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation decline(Accommodation accommodation) {
        accommodation.setStatus(AccommodationStatus.DECLINED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation changeFreeTimeSlotsAcceptingReservation(Long accommodationId, TimeSlotDTO reservationTimeSlot) {
        Accommodation accommodation = removeReservedTimeSlots(findOne(accommodationId),reservationTimeSlot);
        accommodationRepository.save(accommodation);
        return accommodation;
    }

    @Override
    public Accommodation changeFreeTimeSlotsCancelingReservation(Long accommodationId, TimeSlotDTO reservationTimeSlot) {
        Accommodation accommodation = addReservedTimeSlots(findOne(accommodationId),reservationTimeSlot);
        accommodationRepository.save(accommodation);
        return accommodation;
    }

    private static List<LocalDate> convertTimeSlotsToLocalDates(List<TimeSlot> timeSlots) {
        List<LocalDate> localDates = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            LocalDate currentDate = timeSlot.getStartDate();

            while (!currentDate.isAfter(timeSlot.getEndDate())) {
                localDates.add(currentDate);
                currentDate = currentDate.plusDays(1);
            }
        }
        return localDates;
    }

    private static List<LocalDate> mergeAndSort(List<LocalDate> list1, List<LocalDate> list2) {
        List<LocalDate> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        Collections.sort(mergedList);
        return mergedList;
    }

    private List<TimeSlot> createTimeSlots(List<LocalDate> sortedDates) {
        List<TimeSlot> newFreeTimeSlots = new ArrayList<>();
        List<LocalDate> datesForTimeSlot = new ArrayList<>();
        for(LocalDate date:sortedDates){
            if(!sortedDates.contains(date.plusDays(1))){
                datesForTimeSlot.add(date);
            }
            if(!sortedDates.contains(date.minusDays(1))){
                datesForTimeSlot.add(date);
            }
        }
        for(int i=0;i<datesForTimeSlot.size();i++){
            if(i%2==0){
                TimeSlot timeSlot = new TimeSlot(datesForTimeSlot.get(i), datesForTimeSlot.get(i + 1),false);
                timeSlotRepository.save(timeSlot);
                newFreeTimeSlots.add(timeSlot);
            }
        }
        System.out.println(datesForTimeSlot);
        System.out.println(newFreeTimeSlots);
        return newFreeTimeSlots;
    }

    public Accommodation addReservedTimeSlots(Accommodation accommodation, TimeSlotDTO reservationTimeSlot) {
        List<TimeSlot> freeTimeSlots = new ArrayList<>(accommodation.getFreeTimeSlots());
        List<LocalDate> accommodationsFreeDates = convertTimeSlotsToLocalDates(freeTimeSlots);
        List<LocalDate> reservationDates = convertTimeSlotToLocalDates(reservationTimeSlot);
        List<LocalDate> newFreeDates = mergeAndSort(accommodationsFreeDates,reservationDates);
        List<TimeSlot> newFreeTimeSlots = createTimeSlots(newFreeDates);
        System.out.println(accommodationsFreeDates);
        System.out.println(reservationDates);
        System.out.println(newFreeTimeSlots);
        accommodation.setFreeTimeSlots(newFreeTimeSlots);
        return accommodation;
    }

    private List<LocalDate> convertTimeSlotToLocalDates(TimeSlotDTO reservationTimeSlot) {
        List<LocalDate> localDates = new ArrayList<>();
        LocalDate currentDate = reservationTimeSlot.getStartDate();
        while (!currentDate.isAfter(reservationTimeSlot.getEndDate())) {
            localDates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return localDates;
    }



    @Override
    public Collection<Accommodation> findAll(LocalDate begin, LocalDate end, int guestNumber, AccommodationType accommodationType, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities, String hostId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Collection<Accommodation> accommodations=new ArrayList<>();
        if(status!=null){
            accommodations = accommodationRepository.findByStatus(status);
        }
        else{
        int size=0;
        if(amenities!=null){
            size=amenities.size();
        }
        if(begin!=null && end!=null){
                accommodations= accommodationRepository.findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
                        country,
                        city,
                        accommodationType,
                        guestNumber,
                        formatter.format(begin),
                        formatter.format(end),
                        amenities,
                        size,
                        hostId
                );
        }else{
                accommodations= accommodationRepository.findAccommodationsByCountryTypeGuestNumberAndAmenities(
                        country,
                        city,
                        accommodationType,
                        guestNumber,
                        amenities,
                        size,
                        hostId
                );
            }
            System.out.println(accommodations);

            if(endPrice>0 && startPrice>0 && begin!=null && end!=null && guestNumber>0){
                Date beginDate = Date.from(begin.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
                return accommodations.stream()
                        .filter(accommodation ->
                                calculatePriceForAccommodation(accommodation.getId(),
                                        guestNumber,beginDate,endDate) >= startPrice &&
                                        calculatePriceForAccommodation(accommodation.getId(),guestNumber,beginDate,endDate)<= endPrice)
                        .collect(Collectors.toList());
            }
        }
            return accommodations;
    }

    @Override
    public Accommodation findOne(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        accommodation.setStatus(AccommodationStatus.CREATED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Accommodation updatedAccommodation) throws Exception {
        System.out.println(updatedAccommodation);
        Long accommodationId = updatedAccommodation.getId();
        Accommodation existingAccommodation = accommodationRepository.findById(accommodationId).orElse(null);

        if (existingAccommodation == null) {
            throw new Exception("Accommodation not found with ID: " + accommodationId);
        }

        existingAccommodation.setName(updatedAccommodation.getName());
        existingAccommodation.setDescription(updatedAccommodation.getDescription());
        existingAccommodation.setAddress(updatedAccommodation.getAddress());
        existingAccommodation.setAutomaticConfirmation(updatedAccommodation.isAutomaticConfirmation());
        existingAccommodation.setMaxGuests(updatedAccommodation.getMaxGuests());
        existingAccommodation.setMinGuests(updatedAccommodation.getMinGuests());
        existingAccommodation.setPricePerGuest(updatedAccommodation.isPricePerGuest());
        existingAccommodation.setReservationDeadline(updatedAccommodation.getReservationDeadline());
        existingAccommodation.setAmenities(updatedAccommodation.getAmenities());
        existingAccommodation.setType(updatedAccommodation.getType());
        existingAccommodation.setStatus(AccommodationStatus.UPDATED);

        System.out.println(existingAccommodation);

        return accommodationRepository.save(existingAccommodation);
    }

    public Accommodation removeReservedTimeSlots(Accommodation accommodation, TimeSlotDTO reservationTimeSlot) {
        List<TimeSlot> freeTimeSlots = new ArrayList<>(accommodation.getFreeTimeSlots());
        List<TimeSlot> newFreeTimeSlots = new ArrayList<>();
        for(TimeSlot freeTimeSlot: freeTimeSlots){
            LocalDate freeStartDate = freeTimeSlot.getStartDate();
            LocalDate freeEndDate = freeTimeSlot.getEndDate();

            LocalDate reservationStartDate = reservationTimeSlot.getStartDate();
            LocalDate reservationEndDate = reservationTimeSlot.getEndDate();

            if((reservationStartDate.isAfter(freeStartDate) || reservationStartDate.equals(freeStartDate))
                    && reservationStartDate.isBefore(freeEndDate) && reservationEndDate.isAfter(freeStartDate)
                    && (reservationEndDate.isBefore(freeEndDate)||reservationEndDate.equals(freeEndDate))){
                if(!freeStartDate.plusDays(1).equals(reservationStartDate)){
                    TimeSlot timeSlot = new TimeSlot(freeStartDate,reservationStartDate.minusDays(1),false);
                    timeSlotRepository.save(timeSlot);
                    newFreeTimeSlots.add(timeSlot);
                }
                if(!reservationEndDate.plusDays(1).equals(freeEndDate)){
                    TimeSlot timeSlot1 = new TimeSlot(reservationEndDate.plusDays(1),freeEndDate,false);
                    timeSlotRepository.save(timeSlot1);
                    newFreeTimeSlots.add(timeSlot1);
                }
            }
            else{
                newFreeTimeSlots.add(freeTimeSlot);
            }
        }
        accommodation.setFreeTimeSlots(newFreeTimeSlots);
        return accommodation;
    }



    @Override
    public Accommodation editAccommodationFreeTimeSlots(TimeSlotDTO newTimeSlot, Long id) {
        Accommodation accommodationForUpdate = accommodationRepository.findById(id).orElse(null);

        if (accommodationForUpdate == null) {
            return null;
        }

        if (availabilityService.reservationOverlaps(newTimeSlot, accommodationForUpdate.getId())) {
            return null;
        }

        Collection<TimeSlot> timeSlots = accommodationForUpdate.getFreeTimeSlots();

        availabilityService.updateFreeTimeSlots(newTimeSlot, timeSlots); //5

        accommodationRepository.save(accommodationForUpdate);
        return accommodationForUpdate;
    }

    @Override
    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Override
    public Accommodation editAccommodationPricelistItem(PricelistItemDTO price, Long id) {
        boolean check = false;
        Accommodation accommodationForUpdate = findOne(id);

        if (accommodationForUpdate == null) {
            return null;
        }

        System.out.println(accommodationForUpdate);

        for (PricelistItem pricelist : accommodationForUpdate.getPriceList()) {
            LocalDate startDate = pricelist.getTimeSlot().getStartDate();
            LocalDate endDate = pricelist.getTimeSlot().getEndDate();

            if (startDate.isBefore(price.getTimeSlot().getStartDate()) && endDate.isAfter(price.getTimeSlot().getEndDate())) {
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getStartDate());

                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(),false));

                PricelistItem pricelistItem3 = new PricelistItem();
                pricelistItem3.setPrice(pricelist.getPrice());
                pricelistItem3.setTimeSlot(new TimeSlot(price.getTimeSlot().getEndDate(), endDate, false));

                accommodationForUpdate.getPriceList().add(pricelistItem2);
                accommodationForUpdate.getPriceList().add(pricelistItem3);
                check=true;
                break;
            }

            if (startDate.isBefore(price.getTimeSlot().getStartDate()) && endDate.isBefore(price.getTimeSlot().getEndDate())
                    && endDate.isAfter(price.getTimeSlot().getStartDate())) {
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getStartDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
                accommodationForUpdate.getPriceList().add(pricelistItem2);
                check=true;
                break;
            }

            if (startDate.isAfter(price.getTimeSlot().getStartDate()) && endDate.isAfter(price.getTimeSlot().getEndDate()) &&
            price.getTimeSlot().getEndDate().isAfter(startDate)) {
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getEndDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
                accommodationForUpdate.getPriceList().add(pricelistItem2);
                check=true;
                break;
            }

            if (startDate.isAfter(price.getTimeSlot().getStartDate()) && endDate.isBefore(price.getTimeSlot().getEndDate())) {
                pricelist.setPrice(price.getPrice());
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getStartDate());
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getEndDate());
                check=true;
                break;
            }

            //proveriti
            if (startDate.isEqual(price.getTimeSlot().getStartDate()) && endDate.isEqual(price.getTimeSlot().getEndDate())) {
                pricelist.setPrice(price.getPrice());
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getStartDate());
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getEndDate());
                check=true;
                break;
            }
        }
        if (!check) {
            PricelistItem pricelistItem = new PricelistItem();
            pricelistItem.setPrice(price.getPrice());
            pricelistItem.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
            accommodationForUpdate.getPriceList().add(pricelistItem);
        }

        accommodationRepository.save(accommodationForUpdate);
        return accommodationForUpdate;
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        Accommodation accommodation = findOne(id);

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPath + accommodation.getId());

        System.out.println(uploadDir);

        ImageUploadUtil.saveImage(uploadDir, fileName, image);

        accommodation.setImage(fileName);
        accommodationRepository.save(accommodation);
    }

    public List<String> getImages(Long id) throws IOException {
        Accommodation accommodation = findOne(id);
        List<String> base64Images = new ArrayList<>();

        String directoryPath = StringUtils.cleanPath(imagesDirPath + accommodation.getId());
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] imageFiles = directory.listFiles();

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());
                        String base64Image = Base64.getEncoder().encodeToString(imageData);
                        base64Images.add(base64Image);
                    }
                }
            }
        }
        return base64Images;
    }


    public List<Accommodation> data() {
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool", false));
        amenities.add(new Amenity(1L,"pets",false));
        amenities.add(new Amenity(1L,"parking",false));
        List<Accommodation> accommodationList = new ArrayList<>();
        Accommodation accommodation1 = new Accommodation(
                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14", false),
                 2, 4, AccommodationType.HOTEL,
                true, true, null, AccommodationStatus.CREATED,
                3, new ArrayList<>(), amenities, new ArrayList<>(), false
        );

        Accommodation accommodation2 = new Accommodation(
                2L, "Apartment XYZ", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14", false),
                3, 6, AccommodationType.APARTMENT,
                false, false, null, AccommodationStatus.ACCEPTED,
                5, new ArrayList<>(), amenities, new ArrayList<>(), false
        );
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        return accommodationList;
    }
    @Override
    public Accommodation updateRequestApproval(Accommodation accommodation) {
        Long accommodationId = accommodation.getId();
        Accommodation existingAccommodation = accommodationRepository.findById(accommodationId).orElse(null);

        if(existingAccommodation!=null){
            existingAccommodation.setAutomaticConfirmation(accommodation.isAutomaticConfirmation());
            return accommodationRepository.save(existingAccommodation);
        }
        return  new Accommodation();
    }
}
