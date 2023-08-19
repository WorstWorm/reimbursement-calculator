package mapper;

import dto.DisabledDateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateMapper {
    public DisabledDateDto mapToDateDto(LocalDate localDate) {
        return new DisabledDateDto(
                localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth()
        );
    }

    public List<DisabledDateDto> mapToDateDtoList(final List<LocalDate> localDates) {
        return localDates.stream().map(this::mapToDateDto).collect(Collectors.toList());
    }
}
