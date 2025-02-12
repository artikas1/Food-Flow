import * as React from "react";
import { Box, Typography } from "@mui/material"
import { Upload as UploadIcon } from "@mui/icons-material"

interface PhotoUploadProps {
    photo: string | null
    setPhoto: (photo: string | null) => void
}

export default function PhotoUpload({ photo, setPhoto }: PhotoUploadProps) {
    const handlePhotoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]
        if (file) {
            const imageUrl = URL.createObjectURL(file)
            setPhoto(imageUrl)
        }
    }

    return (
        <Box
            sx={{
                border: "2px dashed #ccc",
                borderRadius: 2,
                overflow: "hidden",
                position: "relative",
            }}
        >
            <input type="file" id="photo-upload" accept="image/*" onChange={handlePhotoChange} style={{ display: "none" }} />
            <label
                htmlFor="photo-upload"
                style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    padding: "2rem",
                    cursor: "pointer",
                }}
            >
                {photo ? (
                    <img
                        src={photo || "/placeholder.svg"}
                        alt="Selected photo"
                        style={{ maxWidth: "100%", maxHeight: "300px", objectFit: "cover", borderRadius: "4px" }}
                    />
                ) : (
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", gap: 1 }}>
                        <UploadIcon sx={{ fontSize: 48, color: "text.secondary" }} />
                        <Typography color="text.secondary">Select photo</Typography>
                    </Box>
                )}
            </label>
        </Box>
    )
}

