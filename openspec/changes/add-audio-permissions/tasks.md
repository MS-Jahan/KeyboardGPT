# Tasks: Add Audio Recording Permissions

## Implementation Tasks

### 1. Add Permission Declaration
- [ ] Add `<uses-permission android:name="android.permission.RECORD_AUDIO" />` to AndroidManifest.xml
- [ ] Verify permission is not marked as dangerous or required
- [ ] Test on Android 6.0+ (runtime permissions)

### 2. Create PermissionManager Utility
- [ ] Create `tn.amin.keyboard_gpt.permission.PermissionManager.java`
- [ ] Implement `checkRecordAudioPermission()` method
- [ ] Implement `requestRecordAudioPermission()` method
- [ ] Implement `shouldShowPermissionRationale()` method
- [ ] Add permission result callback interface

### 3. Permission Request Flow
- [ ] Create permission request dialog with explanation
- [ ] Handle permission granted callback
- [ ] Handle permission denied callback
- [ ] Implement "Don't ask again" detection
- [ ] Add settings redirect for manual permission grant

### 4. Testing
- [ ] Test on Android 6.0 (API 23) - first runtime permissions
- [ ] Test on Android 13 (API 33) - latest permission changes
- [ ] Test permission denial scenarios
- [ ] Test "Don't ask again" scenarios
- [ ] Verify no crashes when permission denied

## Validation Criteria
- Permission appears in app settings
- Runtime permission request displays correctly
- App handles all permission states gracefully
- No crashes on permission denial

## Estimated Effort
2-3 hours
