import React from 'react';
import './index.css';
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import {AuthProvider} from './contexts/AuthContext.tsx';
import {Login} from "./components/auth/Login.tsx";
import {SignUp} from "./components/auth/SignUp.tsx";
import {PrivateRoute} from "./routes/PrivateRoute.tsx";
import {Main} from "./components/main/Main.tsx";
import Profile from "./components/profile/Profile.tsx";

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
    return (
        <>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<SignUp/>}/>
                <Route element={<PrivateRoute />}>
                    <Route path="/" element={<Main />} />
                    <Route path="/profile" element={<Profile />} />
                </Route>
                <Route path="/profile/:userId" element={<Profile />} />
            </Routes>
        </>
    );
};

export default App;
