# Tasks: Audio File Management

## Implementation Tasks

### 1. Create AudioCacheConfig Class
- [ ] Create `tn.amin.keyboard_gpt.audio.AudioCacheConfig.java`
- [ ] Define max cache size (100 MB default)
- [ ] Define max file age (1 hour default)
- [ ] Define max file count (10 files default)
- [ ] Define cleanup interval (on app start + every 6 hours)
- [ ] Make configurable via settings if needed

### 2. Create AudioFileManager Class
- [ ] Create `tn.amin.keyboard_gpt.audio.AudioFileManager.java`
- [ ] Implement singleton pattern
- [ ] Implement file creation with unique names
- [ ] Implement file deletion
- [ ] Implement cache cleanup logic
- [ ] Implement storage space checking

### 3. File Creation and Naming
- [ ] Generate unique filenames with timestamp
- [ ] Format: `voice_YYYYMMDD_HHmmss_SSS.{ext}`
- [ ] Include milliseconds for uniqueness
- [ ] Validate filename before creation
- [ ] Handle file creation failures
- [ ] Return File object for use

### 4. Cache Directory Management
- [ ] Get app cache directory via Context.getCacheDir()
- [ ] Create `voice_recordings/` subdirectory
- [ ] Ensure directory exists before file operations
- [ ] Handle directory creation failures
- [ ] Verify write permissions

### 5. Automatic Cleanup Implementation
- [ ] Implement cleanup based on file age
- [ ] Delete files older than 1 hour
- [ ] Implement cleanup based on file count
- [ ] Keep only most recent 10 files
- [ ] Implement cleanup based on cache size
- [ ] Delete oldest files when cache > 100 MB

### 6. Cleanup Triggers
- [ ] Run cleanup on app start
- [ ] Run cleanup after each recording completion
- [ ] Schedule periodic cleanup (WorkManager)
- [ ] Run cleanup on low storage detected
- [ ] Allow manual cleanup via settings

### 7. Storage Space Checking
- [ ] Check available storage before recording
- [ ] Require minimum 50 MB free space
- [ ] Show error if insufficient space
- [ ] Prevent recording start if low space
- [ ] Clear cache if space low and try again

### 8. File Deletion Safety
- [ ] Never delete files currently in use
- [ ] Track active file references
- [ ] Use file locking if needed
- [ ] Handle deletion failures gracefully
- [ ] Retry failed deletions

### 9. Cache Statistics
- [ ] Calculate total cache size
- [ ] Count total cached files
- [ ] Track oldest file age
- [ ] Provide cache info for settings UI
- [ ] Update statistics after cleanup

### 10. Settings Integration
- [ ] Add "Clear voice cache" button to settings
- [ ] Show current cache size in settings
- [ ] Show cache file count
- [ ] Confirm before manual clear
- [ ] Show success message after clear

### 11. Error Handling
- [ ] Handle cache directory access failures
- [ ] Handle file deletion permission errors
- [ ] Handle storage full scenarios
- [ ] Handle corrupt file scenarios
- [ ] Log all errors for debugging

### 12. Background Cleanup
- [ ] Implement WorkManager periodic task
- [ ] Schedule cleanup every 6 hours
- [ ] Use KEEP constraint (device idle)
- [ ] Handle task cancellation
- [ ] Respect battery optimization

### 13. Testing
- [ ] Test file creation and naming
- [ ] Test automatic cleanup by age
- [ ] Test automatic cleanup by count
- [ ] Test automatic cleanup by size
- [ ] Test low storage handling
- [ ] Test concurrent file access
- [ ] Test cleanup on app start
- [ ] Test manual cache clear
- [ ] Verify files deleted correctly
- [ ] Test storage space checking

## Validation Criteria
- Old recordings deleted automatically
- Cache size stays under limit
- No storage bloat over time
- Storage space checked before recording
- Manual cache clear works correctly
- No active files deleted accidentally
- Cleanup runs reliably

## Estimated Effort
5-6 hours
