import React, {useState} from 'react';

import './App.css';
import Header from './components/Header.tsx';
import Drawer from './components/Drawer.tsx';
import Background from './components/Background.tsx';
import AllListingsComponent from './components/AllListingsComponent.tsx';

function App() {
    const [drawerOpen, setDrawerOpen] = useState(false);

    return (
        <>
            <Background />
            <Header onMenuIconClick={() => setDrawerOpen(true)} />
            <Drawer open={drawerOpen} onClose={() => setDrawerOpen(false)} />
            <AllListingsComponent />
        </>
    );
}

export default App;