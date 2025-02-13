import { useState } from "react";
import { Card, CardContent, Button, TextField, Box } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import TitleInput from "./TitleInput.tsx";
import DescriptionInput from "./DescriptionInput.tsx";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import { useProtectedAxios } from "../../hooks/useProtectedAxios.tsx";
import React from "react";
import PhotoUpload from "./PhotoUpload.tsx";
import ExpiryDatePicker from "./ExpiryDatePicker.tsx";
import CategorySelect from "./CategorySelect.tsx";
import FoodDetailsSelect from "./FoodDetailsSelect.tsx";

interface FoodItem {
    id: string;
    title: string;
    description: string;
    category: string;
    city: string;
    expiryDate: string;
    foodDetails: string;
    image: string;
}

interface CreateListingFormProps {
    existingData?: FoodItem;
    isEditMode?: boolean;
    onCancel?: () => void;
    onSuccess?: () => void;
}

export default function CreateListingForm(props: CreateListingFormProps) {
    const { existingData, isEditMode = false, onCancel, onSuccess } = props;
    const navigate = useNavigate();
    const [photo, setPhoto] = useState<File | null>(null);
    const [title, setTitle] = useState(existingData?.title || "");
    const [description, setDescription] = useState(existingData?.description || "");
    const [category, setCategory] = useState<string | null>(existingData?.category || null);
    const [city, setCity] = useState(existingData?.city || "");
    const [expiryDate, setExpiryDate] = useState<Date | null>(
        existingData?.expiryDate ? new Date(existingData.expiryDate) : null
    );
    const [selectedDetail, setSelectedDetail] = useState(existingData?.foodDetails || "");
    const axios = useProtectedAxios();

    // Utility: Convert a base64 string to a File object.
    const base64ToFile = (base64String: string, filename: string): File => {
        // Remove data URL prefix if present
        const arr = base64String.split(',');
        const bstr = arr.length > 1 ? atob(arr[1]) : atob(arr[0]);
        let mime = "image/jpeg";
        if (arr[0]) {
            const match = arr[0].match(/:(.*?);/);
            if (match) {
                mime = match[1];
            }
        }
        const n = bstr.length;
        const u8arr = new Uint8Array(n);
        for (let i = 0; i < n; i++) {
            u8arr[i] = bstr.charCodeAt(i);
        }
        return new File([u8arr], filename, { type: mime });
    };

    const handleSubmit = async () => {
        const requiredFields = !title || !description || !city || !expiryDate;
        // For creation, photo is required. For updates, if a new photo isn’t selected, we will convert the existing one.
        if (isEditMode ? requiredFields : requiredFields || !photo) {
            alert("Please fill in all required fields.");
            return;
        }

        const formData = new FormData();
        formData.append("title", title);
        formData.append("description", description);
        if (category) formData.append("category", category);
        formData.append("city", city);
        if (expiryDate) {
            formData.append("expiryDate", expiryDate.toISOString().split("T")[0]);
        } else {
            alert("Expiry date is required.");
            return;
        }
        if (selectedDetail) formData.append("foodDetails", selectedDetail);

        // Append the image file
        if (photo) {
            formData.append("image", photo);
        } else if (existingData?.image) {
            // Ensure the base64 string has the proper prefix.
            let base64String = existingData.image;
            if (!base64String.startsWith("data:")) {
                base64String = `data:image/jpeg;base64,${base64String}`;
            }
            const fileFromBase64 = base64ToFile(base64String, "existing.jpg");
            formData.append("image", fileFromBase64);
        }

        try {
            const url = isEditMode
                ? API_ENDPOINTS.UPDATE_FOOD(existingData!.id)
                : API_ENDPOINTS.CREATE_FOOD;
            const method = isEditMode ? "put" : "post";

            await axios[method](url, formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            onSuccess?.();
            navigate("/");
        } catch (error) {
            console.error("Error submitting form:", error);
        }
    };

    return (
        <Card>
            <CardContent sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
                <PhotoUpload
                    photo={photo}
                    setPhoto={setPhoto}
                    existingImageUrl={
                        existingData?.image
                            ? `data:image/jpeg;base64,${existingData.image}`
                            : undefined
                    }
                />
                <TitleInput value={title} onChange={(e) => setTitle(e.target.value)} />
                <DescriptionInput value={description} onChange={(e) => setDescription(e.target.value)} />
                <TextField label="City" value={city} onChange={(e) => setCity(e.target.value)} fullWidth />
                <ExpiryDatePicker date={expiryDate} setDate={setExpiryDate} />
                <CategorySelect selectedCategory={category} setSelectedCategory={setCategory} />
                <FoodDetailsSelect selectedDetail={selectedDetail} setSelectedDetail={setSelectedDetail} />

                <Box sx={{ display: "flex", gap: 2, justifyContent: "flex-end", width: "100%" }}>
                    {isEditMode && (
                        <Button onClick={onCancel} variant="outlined" color="primary">
                            Cancel
                        </Button>
                    )}
                    <Button onClick={handleSubmit} variant="contained" color="primary">
                        {isEditMode ? "Update" : "Create"}
                    </Button>
                </Box>
            </CardContent>
        </Card>
    );
}
