import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

interface DrawerProps {
    open: boolean;
    onClose: () => void;
}

export default function DrawerComponent({ open, onClose }: DrawerProps) {
    const [openCategories, setOpenCategories] = useState(false);
    const navigate = useNavigate();

    const handleNavigation = (path: string) => {
        navigate(path);
        onClose();
    };

    const DrawerList = (
        <Box
            sx={{
                width: 250,
                color: 'white',
                backgroundColor: '#92AE5A',
            }}
            role="presentation"
        >
            <List>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => handleNavigation('/')}>
                        <ListItemText primary="All listings" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => handleNavigation('/profile')}>
                        <ListItemText primary="My listings" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => handleNavigation('/my-reservations')}>
                        <ListItemText primary="My reservations" />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    );

    return (
        <Drawer
            open={open}
            onClose={onClose}
            sx={{
                '& .MuiDrawer-paper': {
                    backgroundColor: '#92AE5A',
                },
            }}
        >
            {DrawerList}
        </Drawer>
    );
}