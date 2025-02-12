import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import Collapse from '@mui/material/Collapse';
import {useState} from "react";
import {API_ENDPOINTS} from "../../apiConfig.ts";
import useFetchData from "../../hooks/useFetchData.tsx";

interface DrawerProps {
    open: boolean;
    onClose: () => void;
}

export default function DrawerComponent({open, onClose}: DrawerProps) {
    const [openCategories, setOpenCategories] = useState(false);

    const formatCategoryName = (category: string) => {
        return category
            .split('_')
            .map((word, index) => {
                return index === 0 ? word.charAt(0).toUpperCase() + word.slice(1).toLowerCase() : word.toLowerCase();
            })
            .join(' ');
    };

    const handleToggleCategories = () => {
        setOpenCategories(!openCategories);
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
                {['All listings', 'My listings'].map((text) => (
                    <ListItem key={text} disablePadding>
                        <ListItemButton>
                            <ListItemText primary={text}/>
                        </ListItemButton>
                    </ListItem>
                ))}
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