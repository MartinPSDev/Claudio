package com.anthropic.claude.login

/**
 * Provides information about MDM/enterprise managed login restrictions.
 */
interface ManagedLoginProvider {
    /** Returns true if the user is allowed to proceed with the current login method. */
    fun isManagedLoginAllowed(): Boolean
}
