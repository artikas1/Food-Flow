"use client"

import { useState } from "react"
import { Card, CardContent, Button } from "@mui/material"
import PhotoUpload from "./PhotoUpload.tsx"
import TitleInput from "./TitleInput.tsx"
import DescriptionInput from "./DescriptionInput.tsx"
import ExpiryDatePicker from "./ExpiryDatePicker.tsx"
import CategorySelect from "./CategorySelect.tsx"
import FoodDetailsSelect from "./FoodDetailsSelect.tsx"

export default function CreateListingForm() {
    const [photo, setPhoto] = useState<string | null>(null)
    const [date, setDate] = useState<Date | null>(null)
    const [selectedDetails, setSelectedDetails] = useState<string[]>([])

    return (
        <Card>
            <CardContent sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
                <PhotoUpload photo={photo} setPhoto={setPhoto} />

                <TitleInput />
                <DescriptionInput />
                <ExpiryDatePicker date={date} setDate={setDate} />
                <CategorySelect />
                <FoodDetailsSelect selectedDetails={selectedDetails} setSelectedDetails={setSelectedDetails} />

                <Button variant="contained" color="primary" sx={{ alignSelf: "flex-end" }}>
                    Create
                </Button>
            </CardContent>
        </Card>
    )
}

