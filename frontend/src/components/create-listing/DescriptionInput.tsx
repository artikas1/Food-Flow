import { TextField } from "@mui/material"

export default function DescriptionInput() {
    return (
        <TextField
            fullWidth
            id="description"
            label="Description"
            placeholder="For ex. need to be stored inside fridge"
            variant="outlined"
            multiline
            rows={4}
        />
    )
}

