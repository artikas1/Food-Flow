import { useState } from "react";
import axios from "axios";
import { Card, CardContent, Button, TextField } from "@mui/material";
import PhotoUpload from "./PhotoUpload.tsx";
import TitleInput from "./TitleInput.tsx";
import DescriptionInput from "./DescriptionInput.tsx";
import ExpiryDatePicker from "./ExpiryDatePicker.tsx";
import CategorySelect from "./CategorySelect.tsx";
import FoodDetailsSelect from "./FoodDetailsSelect.tsx";
import React from "react";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import {useProtectedAxios} from "../../hooks/useProtectedAxios.tsx";

export default function CreateListingForm() {
    const [photo, setPhoto] = useState<File | null>(null);
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [category, setCategory] = useState<string | null>(null);
    const [city, setCity] = useState("");
    const [expiryDate, setExpiryDate] = useState<Date | null>(null);
    const [selectedDetail, setSelectedDetail] = useState<string>("");
    const axios = useProtectedAxios();

    const handleSubmit = async () => {
        if (!title || !description || !city || !expiryDate || !photo) {
            alert("Please fill in all required fields.");
            return;
        }

        const formData = new FormData();
        formData.append("title", title);
        formData.append("description", description);
        if (category) formData.append("category", category);
        formData.append("city", city);
        formData.append("expiryDate", expiryDate.toISOString().split("T")[0]);
        if (selectedDetail) {
            formData.append("foodDetails", selectedDetail);
        }
        formData.append("image", photo);

        try {
            const response = await axios.post(API_ENDPOINTS.CREATE_FOOD, formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            console.log("Success:", response.data);
        } catch (error) {
            console.error("Error submitting form:", error);
        }
    };

    return (
        <Card>
            <CardContent sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
                <PhotoUpload photo={photo} setPhoto={setPhoto} />
                <TitleInput value={title} onChange={(e) => setTitle(e.target.value)} />
                <DescriptionInput value={description} onChange={(e) => setDescription(e.target.value)} />
                <TextField label="City" value={city} onChange={(e) => setCity(e.target.value)} fullWidth />
                <ExpiryDatePicker date={expiryDate} setDate={setExpiryDate} />
                <CategorySelect selectedCategory={category} setSelectedCategory={setCategory} />
                <FoodDetailsSelect selectedDetail={selectedDetail} setSelectedDetail={setSelectedDetail} />

                <Button onClick={handleSubmit} variant="contained" color="primary" sx={{ alignSelf: "flex-end" }}>
                    Create
                </Button>
            </CardContent>
        </Card>
    );
}