// @ts-ignore
import CreateListingForm from "./CreateListingForm.tsx"

import { ThemeProvider, createTheme } from "@mui/material/styles"
import CssBaseline from "@mui/material/CssBaseline"

const theme = createTheme({
    palette: {
        primary: {
            main: "#9ab76c",
        },
        background: {
            default: "#e9fade",
        },
    },
})

export default function CreateListingPage() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <div style={{ minHeight: "100vh" }}>

                <main style={{ maxWidth: "42rem", margin: "2rem auto", padding: "0 1rem" }}>
                    <CreateListingForm />
                </main>
            </div>
        </ThemeProvider>
    )
}

