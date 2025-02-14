import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { IoLocationOutline } from "react-icons/io5";
import Avatar from "@mui/material/Avatar";
import { useNavigate } from "react-router-dom";

interface FoodCardListingProps {
    id: string;
    title: string;
    city: string;
    image: string;
    rating: string;
    availability: boolean;
}

export default function FoodCardListing({ id, title, city, image, rating, availability }: FoodCardListingProps) {
    const theme = useTheme();
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/listings/${id}`);
    };

    return (
        <Card
            sx={{
                display: 'flex',
                maxWidth: '50%',
                width: '35%',
                margin: 'auto',
                marginTop: '1rem',
                borderRadius: 4,
                '&:hover': {
                    cursor: 'pointer',
                    backgroundColor: theme.palette.grey[200],
                },
            }}
            onClick={handleClick}
        >
            <CardMedia
                component="img"
                sx={{ width: 151, height: 151, objectFit: 'cover' }}
                image={`data:image/jpeg;base64,${image}`}
                alt="Food picture"
            />
            <Box sx={{ display: 'flex', flexDirection: 'column' }}>
                <CardContent sx={{ flex: '1 0 auto', pb: 1 }}>
                    <Typography component="div" variant="h6" sx={{ fontWeight: 600 }}>
                        {title}
                    </Typography>

                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
                        <IoLocationOutline size={16} />
                        <Typography
                            variant="subtitle1"
                            component="div"
                            sx={{
                                color: 'text.secondary',
                                fontSize: '1rem'
                            }}
                        >
                            {city}
                        </Typography>
                    </Box>

                    {/* Display availability here */}
                    <Typography
                        variant="subtitle2"
                        component="div"
                        sx={{
                            color: availability ? 'green' : 'red',
                            fontSize: '0.9rem',
                            marginTop: 1
                        }}
                    >
                        Availability: {availability ? 'Available' : 'Not Available'}
                    </Typography>
                </CardContent>

                <Box sx={{ display: 'flex', alignItems: 'center', pl: 1, pb: 1 }}>
                    <Avatar
                        alt="U"
                        src="/static/images/avatar/2.jpg"
                        sx={{ width: 38, height: 38, marginLeft: 'auto' }}
                    />
                    <Typography
                        variant="subtitle1"
                        component="div"
                        sx={{
                            color: 'text.secondary',
                            fontSize: '0.9rem',
                            marginLeft: 'auto'
                        }}
                    >
                        {rating}
                    </Typography>
                </Box>
            </Box>
        </Card>
    );
}