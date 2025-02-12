import { TextField } from "@mui/material";
import React from "react";

interface TitleInputProps {
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function TitleInput({ value, onChange }: TitleInputProps) {
    return (
        <TextField
            fullWidth
            id="title"
            label="Title"
            placeholder="For ex. Greek yogurt"
            variant="outlined"
            value={value}
            onChange={onChange}
        />
    );
}
