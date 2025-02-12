// @ts-ignore
import React, { useState } from "react";
import './index.css';
import CreateListingPage from "./components/create-listing/CreateFoodFormPage.tsx";



function App() {
    const [selectedDetails, setSelectedDetails] = useState<string[]>([]);
    const [date, setDate] = useState<Date | null>(null);
    const [photo, setPhoto] = useState<string | null>(null);

    return (
        <div className="App">
            <CreateListingPage/>


        </div>
    );
}

export default App;