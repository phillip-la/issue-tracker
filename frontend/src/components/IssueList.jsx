function IssueList({ issues, onDeleteIssue, onEditIssue }) {
    return (
        <ul>
            {
                issues.map(issue => (
                    <li key={issue.id}>
                        {issue.title} - {issue.priority}

                        <button
                            type="button"
                            onClick={() => onDeleteIssue(issue.id)}
                        >
                            Delete
                        </button>

                        <button
                            type="button"
                            onClick={() => onEditIssue(issue)}
                        >
                            Edit
                        </button>
                    </li>
                ))
            }
        </ul>
    )
}

export default IssueList