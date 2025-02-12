import React from "react";

interface ExpiryDatePickerProps {
    date: Date | null;
    setDate: (date: Date | null) => void;
}

export default function ExpiryDatePicker({ date, setDate }: ExpiryDatePickerProps) {
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newDate = event.target.value ? new Date(event.target.value) : null;
        setDate(newDate);
    };

    return (
        <div className="flex flex-col space-y-2">
            <label htmlFor="expiry-date" className="text-sm font-medium text-gray-700">
                Expiry Date
            </label>
            <input
                id="expiry-date"
                type="date"
                value={date ? date.toISOString().split("T")[0] : ""}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2 w-full text-gray-900 focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
        </div>
    );
}
