import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TextField } from "@mui/material";

interface ExpiryDatePickerProps {
    date: Date | null;
    setDate: (date: Date | null) => void;
}

export default function ExpiryDatePicker({ date, setDate }: ExpiryDatePickerProps) {
    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
                label="Expiry date"
                value={date}
                onChange={(newValue) => {
                    setDate(newValue);
                }}
                slots={{ textField: (params) => <TextField {...params} fullWidth /> }}
            />
        </LocalizationProvider>
    );
}
