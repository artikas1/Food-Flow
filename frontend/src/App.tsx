import React, {useState} from 'react';
import './index.css';
import {Route, BrowserRouter as Router, Routes, useLocation } from "react-router-dom";
import {AuthProvider} from './contexts/AuthContext.tsx';
import {Login} from "./components/auth/Login.tsx";
import {SignUp} from "./components/auth/SignUp.tsx";
import {PrivateRoute} from "./routes/PrivateRoute.tsx";
import {Main} from "./components/main/Main.tsx";
import CreateListingPage from "./components/create-listing/CreateFoodFormPage.tsx";
import Header from "./components/main/Header.tsx";
import DrawerComponent from "./components/main/Drawer.tsx";
import ReservationsPage from "./components/reservation/ReservationsPage.tsx";
import FoodDetailsPage from "./components/food/FoodDetailsPage.tsx";

function App() {
    return (
        <Router>
            <AuthProvider>
                <MainContent/>
            </AuthProvider>
        </Router>
    );
}

const MainContent: React.FC = () => {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);
    const location = useLocation();
    const hideHeader = location.pathname === '/login' || location.pathname === '/signup';
    const hideDrawer = location.pathname === '/login' || location.pathname === '/signup';

    const handleOnMenuIconClick = () => {
        setIsDrawerOpen(true);
    };

    const handleDrawerClose = () => {
        setIsDrawerOpen(false);
    };

    return (
        <>
            {!hideHeader && <Header onMenuIconClick={handleOnMenuIconClick} />}
            {!hideDrawer && <DrawerComponent open={isDrawerOpen} onClose={handleDrawerClose} />}
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<SignUp/>}/>
                <Route element={<PrivateRoute />}>
                    <Route path="/" element={<Main />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/create-listing" element={<CreateListingPage />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/my-reservations" element={<ReservationsPage />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/listings/:id" element={<FoodDetailsPage />} />
                </Route>
            </Routes>
        </>
    );
};

export default App;