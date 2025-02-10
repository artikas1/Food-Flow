import { Box } from '@mui/material';

function Background() {
    return (
        <Box
            sx={{
                backgroundColor: 'rgba(165, 210, 28, 0.09)',
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                zIndex: -1
            }}
        />
    );
}

export default Background;