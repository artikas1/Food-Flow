import { TextField } from "@mui/material";
import React from "react";

interface DescriptionInputProps {
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function DescriptionInput({ value, onChange }: DescriptionInputProps) {
    return (
        <TextField
            fullWidth
            id="description"
            label="Description"
            placeholder="For ex. Needs to be stored inside the fridge"
            variant="outlined"
            multiline
            rows={4}
            value={value}
            onChange={onChange}
        />
    );
}
