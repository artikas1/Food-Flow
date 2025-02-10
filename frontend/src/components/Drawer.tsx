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
import axios from 'axios';
import {useEffect, useState} from "react";

const API = "http://localhost:8080/api/v1/food/categories";

interface DrawerProps {
    open: boolean;
    onClose: () => void;
}

export default function TemporaryDrawer({ open, onClose }: DrawerProps) {
    const [openCategories, setOpenCategories] = useState(false);
    const [categories, setCategories] = useState<string[]>([]);
    const formatCategoryName = (category: string) => {
        return category
            .split('_')
            .map((word, index) => {
                return index === 0 ? word.charAt(0).toUpperCase() + word.slice(1).toLowerCase() : word.toLowerCase();
            })
            .join(' ');
    };

    useEffect(() => {
        axios.get(API)
            .then(response => {
                const formattedCategories = response.data.map((category: string) => formatCategoryName(category));
                setCategories(formattedCategories);
            })
            .catch(error => console.error("Error fetching categories:", error));
    }, []);

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
                {['All listings', 'My listings'].map((text, index) => (
                    <ListItem key={text} disablePadding>
                        <ListItemButton>
                            <ListItemText primary={text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
            <Divider />

            {/* Categories Dropdown */}
            <ListItem disablePadding>
                <ListItemButton onClick={handleToggleCategories}>
                    <ListItemText primary="Categories" />
                    {openCategories ? <ExpandLessIcon /> : <ExpandMoreIcon />}
                </ListItemButton>
            </ListItem>
            <Collapse in={openCategories} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    {categories.length > 0 ? (
                        categories.map((category) => (
                            <ListItem key={category} disablePadding>
                                <ListItemButton sx={{ pl: 4 }}>
                                    <ListItemText primary={category} />
                                </ListItemButton>
                            </ListItem>
                        ))
                    ) : (
                        <ListItem disablePadding>
                            <ListItemButton>
                                <ListItemText primary="No categories available" />
                            </ListItemButton>
                        </ListItem>
                    )}
                </List>
            </Collapse>

            <Divider />
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
