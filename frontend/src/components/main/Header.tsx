import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import '@fontsource/caveat';

const settings = ['Profile', 'Logout'];

interface HeaderProps {
    onMenuIconClick: () => void;
}

function ResponsiveAppBar({ onMenuIconClick }: HeaderProps) {
    const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);
    const navigate = useNavigate();

    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleMenuItemClick = (setting: string) => {
        if (setting === 'Profile') {
            navigate('/profile');
        } else if (setting === 'Logout') {
            navigate('/login');
        }
        handleCloseUserMenu();
    };

    return (
        <AppBar position="static" sx={{ backgroundColor: 'white', boxShadow: 2 }}>
            <Container maxWidth="xl" sx={{ boxShadow: 2 }}>
                <Toolbar disableGutters>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={onMenuIconClick}
                    >
                        <MenuIcon sx={{ color: '#AEC761' }} />
                    </IconButton>
                    <Typography
                        variant="h6"
                        component="a"
                        sx={{
                            mr: 2,
                            display: { xs: 'none', md: 'flex' },
                            fontFamily: '"Caveat", cursive',
                            fontWeight: 700,
                            color: '#AEC761',
                            textDecoration: 'none',
                            fontSize: '34px',
                            cursor: 'pointer'
                        }}
                        onClick={() => navigate('/')}
                    >
                        FoodFlow
                    </Typography>
                    <Typography
                        variant="h5"
                        component="a"
                        sx={{
                            mr: 2,
                            display: { xs: 'flex', md: 'none' },
                            flexGrow: 1,
                            fontFamily: '"Caveat", cursive',
                            fontWeight: 700,
                            color: '#AEC761',
                            textDecoration: 'none',
                        }}
                    >
                        FoodFlow
                    </Typography>
                    <Box sx={{ marginLeft: 'auto', display: 'flex', alignItems: 'center', gap: 2 }}>
                        <Tooltip title="Open settings">
                            <IconButton onClick={handleOpenUserMenu} sx={{ marginLeft: 'auto' }}>
                                <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                            </IconButton>
                        </Tooltip>
                        <Menu
                            sx={{ mt: '45px' }}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                            keepMounted
                            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >
                            {settings.map((setting) => (
                                <MenuItem key={setting} onClick={() => handleMenuItemClick(setting)}>
                                    <Typography textAlign="center">{setting}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                        <Box sx={{ marginLeft: 'auto' }}>
                            <Button
                                variant="contained"
                                sx={{
                                    backgroundColor: '#AEC761',
                                    color: 'white',
                                    '&:hover': { backgroundColor: '#94A857' }
                                }}
                                onClick={() => navigate('/create-listing')}
                            >
                                Create listing
                            </Button>
                        </Box>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default ResponsiveAppBar;