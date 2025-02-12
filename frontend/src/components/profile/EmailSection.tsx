import React, { useState } from 'react';
import { Box, Typography, IconButton, Paper } from '@mui/material';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';

interface EmailSectionProps {
  email: string;
}

const EmailSection: React.FC<EmailSectionProps> = ({ email }) => {
  const [showEmail, setShowEmail] = useState(false);

  return (
    <Paper variant="outlined" sx={{ p: 3, width: '100%', maxWidth: 480, borderRadius: 3, mb: 3 }}>
      <Box sx={{ display: 'flex', alignItems: 'center' }}>
        <Typography variant="subtitle1" sx={{ fontWeight: 500, mr: 2 }}>
          Email Address
        </Typography>
        <Typography variant="body1" color="text.secondary" sx={{ mr: 1 }}>
          {showEmail ? email : '••••••••••'}
        </Typography>
        <IconButton onClick={() => setShowEmail(!showEmail)} size="small">
          {showEmail ? <VisibilityOffIcon /> : <VisibilityIcon />}
        </IconButton>
      </Box>
    </Paper>
  );
};

export default EmailSection;