import React from 'react';
import './index.css';
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import {AuthProvider} from './contexts/AuthContext.tsx';
import {Login} from "./components/auth/Login.tsx";
import {SignUp} from "./components/auth/SignUp.tsx";

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
            </Routes>
        </>
    );
};

export default App;