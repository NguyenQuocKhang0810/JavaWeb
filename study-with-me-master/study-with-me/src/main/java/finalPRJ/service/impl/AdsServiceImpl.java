package finalPRJ.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Part;

import finalPRJ.constants.ErrorMessage;
import finalPRJ.model.PaginationModel;
import finalPRJ.model.request.AdsModelRequest;
import finalPRJ.model.response.AdsModelResponse;
import finalPRJ.repository.AdsRepository;
import finalPRJ.service.AdsService;
import finalPRJ.utils.FileUtil;
import finalPRJ.utils.StringUtil;

public class AdsServiceImpl implements AdsService {
    private AdsRepository adsRepository = new AdsRepository();
    @Override
    public boolean create(AdsModelRequest request, Collection<Part> images, String username) {
        // Validate title is exists.
        AdsModelResponse oldAds = adsRepository.getByPosition(request.getPosition());
        if (oldAds != null) {
            throw new RuntimeException(ErrorMessage.NAME_IS_EXISTS);
        }
        // Handle file uploads
        if (images != null && !images.isEmpty()) {
            List<String> fileNames = images.stream()
                .filter(part -> part.getSize() > 0) // Ensure the file part is not empty
                .map(FileUtil::saveFile) // Save the file and get the file name
                .collect(Collectors.toList());
            // Set filenames saved to Model. Assuming images can be a list of filenames
            request.setImages(String.join(",", fileNames)); // Join filenames with comma or any other delimiter
        }
        // Call repository to save Ads
        return adsRepository.create(request, username);
    }

    @Override
    public boolean update(String id, AdsModelRequest request, Collection<Part> images, String username) {
        // Parse String to Long.
        Long idNumber = StringUtil.parseLong("id", id);
        // Get old Ads.
        AdsModelResponse oldAds = adsRepository.getById(idNumber);
        if (oldAds == null) {
            throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
        }
        // Compare is title change.
        if (!request.getPosition().equalsIgnoreCase(oldAds.getPosition())) {
            // Compare new title with other name in database.
            AdsModelResponse otherAds = adsRepository.getByPosition(request.getPosition());
            if (otherAds != null) {
                throw new RuntimeException(ErrorMessage.NAME_IS_EXISTS);
            }
        }
        // Handle file uploads
        if (images != null && !images.isEmpty()) {
            // Delete old images if exist
            if (oldAds.getImages() != null) {
                String[] oldImageFiles = oldAds.getImages().split(",");
                for (String oldImage : oldImageFiles) {
                    FileUtil.removeFile(oldImage);
                }
            }
            // Save new images
            List<String> fileNames = images.stream()
                .filter(part -> part.getSize() > 0)
                .map(FileUtil::saveFile)
                .collect(Collectors.toList());
            request.setImages(String.join(",", fileNames));
        } else {
            // If no new images, reuse old ones
            request.setImages(oldAds.getImages());
        }

        // Call repository to update Ads
        return adsRepository.update(idNumber, request, username);
    }

    @Override
    public boolean deleteById(String id) {
        Long idNumber = StringUtil.parseLong("id", id);
        AdsModelResponse oldAds = adsRepository.getById(idNumber);
        if (oldAds == null) {
            throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
        }

        // Delete old images if exist
        if (oldAds.getImages() != null) {
            String[] imageFiles = oldAds.getImages().split(",");
            for (String image : imageFiles) {
                FileUtil.removeFile(image);
            }
        }
        return adsRepository.deleteById(idNumber);
    }

    @Override
    public AdsModelResponse getByPosition(String position) {
        AdsModelResponse oldAds = adsRepository.getByPosition(position);
        if (oldAds == null) {
            throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
        }
        return oldAds;
    }

    @Override
    public AdsModelResponse getById(String id) {
        Long idNumber = StringUtil.parseLong("id", id);
        AdsModelResponse oldAds = adsRepository.getById(idNumber);
        if (oldAds == null) {
            throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
        }
        return oldAds;
    }
    
    @Override
    public PaginationModel filter(String page, String size, String name) {
        int pageNumber = StringUtil.parseInt("Page", page);
        int sizeNumber = StringUtil.parseInt("Size", size);
        return adsRepository.filterByName(pageNumber, sizeNumber, name);
    }
}
